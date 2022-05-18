package io.sunshower.realms.cryptkeeper;

import io.sunshower.arcus.condensation.Condensation;
import io.sunshower.crypt.DefaultSecretService;
import io.sunshower.crypt.JCAEncryptionService;
import io.sunshower.crypt.core.EncryptionService;
import io.sunshower.crypt.core.EncryptionServiceSet;
import io.sunshower.crypt.core.Leases;
import io.sunshower.crypt.core.SecretService;
import io.sunshower.crypt.core.VaultLease;
import io.sunshower.crypt.secrets.StringSecret;
import io.sunshower.crypt.vault.AuthenticationFailedException;
import io.sunshower.lang.common.encodings.Encoding;
import io.sunshower.lang.common.encodings.Encodings;
import io.sunshower.lang.common.encodings.Encodings.Type;
import io.sunshower.lang.tuple.Pair;
import io.sunshower.model.api.User;
import io.sunshower.persistence.id.Identifier;
import io.sunshower.persistence.id.Identifiers;
import io.sunshower.persistence.id.Sequence;
import io.sunshower.realms.RealmManager;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@Log
public class FileBackedCryptKeeperRealm
    extends AbstractUserDetailsAuthenticationProvider
    implements RealmManager {


  static final Encoding encoding;
  /**
   * need a global lock on this one
   */
  private static final Object lock;
  private static final Sequence<Identifier> sequence;

  static {
    lock = new Object();
    encoding = Encodings.create(Type.Base58);
    sequence = Identifiers.newSequence(true);
  }

  private final File userdb;
  private final Condensation condensation;
  private final SecretService secretService;
  private EncryptionService encryptionService;
  private boolean locked;
  private VaultLease vaultLease;
  private UserDatabase userDatabase;
  private RealmConfiguration configuration;


  public FileBackedCryptKeeperRealm(@NonNull RealmConfiguration configuration) {
    this.configuration = configuration;
    this.userdb = checkBase(configuration.getBase());
    this.condensation = Condensation.create("json");
    this.secretService = new DefaultSecretService(configuration.getBase(), condensation);
    unlock(configuration.getPassword());
    this.encryptionService = new JCAEncryptionService(encoding.encode(configuration.getSalt()),
        configuration.getPassword());
    ((JCAEncryptionService) encryptionService).setInitializationVector(
        encoding.encode(configuration.getInitializationVector()));
  }


  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

  }

  @Override
  protected UserDetails retrieveUser(String username,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    return findByUsername(username).flatMap(
            u -> doAuthenticate(u, String.valueOf(authentication.getCredentials())))
        .orElseThrow(
            () -> new AuthenticationFailedException("Error: no user with those credentials!"));
  }


  @Override
  public void lock() {
    synchronized (lock) {
      if (!locked) {
        flush();
        vaultLease.close();
        locked = true;
      }
    }
  }


  @Override
  public boolean isLocked() {
    return locked;
  }

  @Override
  public void unlock(CharSequence password) {
    this.configuration = new RealmConfiguration(configuration.getBase(), password);
    val results = readUserDatabase();
    configuration.setSalt(encoding.decode(userDatabase.getSalt()));
    configuration.setInitializationVector(encoding.decode(userDatabase.getInitializationVector()));
    this.vaultLease = results.fst;
    this.userDatabase = results.snd;
    this.encryptionService = new JCAEncryptionService(encoding.encode(configuration.getSalt()),
        configuration.getPassword());
    ((JCAEncryptionService) encryptionService).setInitializationVector(
        encoding.encode(configuration.getInitializationVector()));
    this.locked = false;
  }

  @Override
  public Identifier saveUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User must not be null");
    }
    val encryptionSet = secretService.createEncryptionServiceSet(configuration.getPassword());
    user.setSalt(encryptionSet.getSalt());
    user.setInitializationVector(encryptionSet.getInitializationVector());
    user.setId(sequence.next());
    updatePassword(user, encryptionSet);
    userDatabase.addUser(user);
    val details = user.getDetails();
    if (details != null) {
      details.setId(user.getId());
    }
    user.setCreated(new Date());
    flush();
    return user.getId();
  }

  @Override
  public void saveAll(Collection<User> users) {
    for (val user : users) {
      val encryptionSet = secretService.createEncryptionServiceSet(configuration.getPassword());
      user.setSalt(encryptionSet.getSalt());
      user.setInitializationVector(encryptionSet.getInitializationVector());
      updatePassword(user, encryptionSet);
      user.setId(sequence.next());
      user.setCreated(new Date());
      val details = user.getDetails();
      if (details != null) {
        details.setId(user.getId());
      }
      userDatabase.addUser(user);
    }
    flush();
  }



  @Override
  public void deleteUser(User user) {
    userDatabase.removeUser(user);
    vaultLease.delete(user.getId());
    flush();
  }

  @Override
  public List<User> getUsers() {
    return new ArrayList<>(userDatabase.getUsers());
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return Optional.ofNullable(userDatabase.findByUsername(username));
  }

  @Override
  public Optional<User> authenticate(String username, String password) {
    return findByUsername(username)
        .flatMap(u -> doAuthenticate(u, password));
  }

  @Override
  public User getUser(Identifier id) {
    return userDatabase.getUser(id);
  }


  @Override
  public void close() {
    flush();
    lock();
  }

  private File checkBase(File base) {
    if (base == null) {
      throw new IllegalArgumentException("Base file for FileBackedCryptKeeper must not be null");
    }

    if (base.exists()) {
      if (!base.isDirectory()) {
        throw new IllegalArgumentException(
            "Error: base file %s exists, but is not a directory".formatted(base));
      }
      if (!base.canRead()) {
        throw new IllegalArgumentException(
            "Error: base file %s exists, cannot be read".formatted(base));
      }
      if (!base.canWrite()) {
        throw new IllegalArgumentException(
            "Error: base file %s exists, cannot be written".formatted(base));
      }
    } else {
      log.log(Level.INFO, "Directory {0} does not exist--attempting to create it", base);
      if (!base.mkdirs()) {
        log.log(Level.SEVERE, "Failed to create directory {0}--please fix the issue and try again",
            base);
      }
    }

    val userdb = new File(base, "user.db");
    if (userdb.exists()) {
      if (!userdb.isFile()) {
        throw new IllegalArgumentException(
            "Error: user database file '%s' exists, but is not a file".formatted(userdb));
      }
      if (!userdb.canRead()) {
        throw new IllegalArgumentException(
            "Error: user database file '%s' exists, cannot be read ".formatted(userdb));
      }
      if (!userdb.canWrite()) {
        throw new IllegalArgumentException(
            "Error: user database file '%s' exists, cannot be written ".formatted(userdb));
      }
    } else {
      try {
        if (!userdb.createNewFile()) {
          throw new IllegalArgumentException(
              "Error: failed to create new file '%s'".formatted(userdb));
        }
      } catch (IOException e) {
        throw new IllegalArgumentException(
            "Error: failed to create new file '%s', reason: %s".formatted(userdb, e.getMessage()));
      }
    }
    return userdb;
  }

  @SneakyThrows
  private void flush() {
    synchronized (lock) {
      try (val output = new OutputStreamWriter(
          new BufferedOutputStream(new FileOutputStream(userdb)))) {
        output.write(condensation.write(UserDatabase.class, userDatabase));
        output.flush();
      }
    }
  }

  private Pair<VaultLease, UserDatabase> readUserDatabase() {
    synchronized (lock) {
      try (val input = new BufferedInputStream(new FileInputStream(userdb))) {
        Identifier vaultId;
        VaultLease vaultLease;
        val leaseRequest = Leases.forPassword(configuration.getPassword())
            .expiresIn(1, TimeUnit.DAYS);
        try {
          userDatabase = condensation.read(UserDatabase.class, input);
          vaultId = userDatabase.getVaultId();
          vaultLease = secretService.lease(vaultId, leaseRequest);
        } catch (NoSuchElementException ex) {
          // database hasn't been saved
          val vault = secretService.createVault("Default Vault",
              "Default Vault for secrets management",
              configuration.getPassword());
          vaultLease = secretService.lease(vault.getId(), leaseRequest);
          userDatabase = new UserDatabase(vault.getId());
          userDatabase.setSalt(encoding.encode(configuration.getSalt()));
          userDatabase.setInitializationVector(
              encoding.encode(configuration.getInitializationVector()));
          flush();
        }
        return Pair.of(vaultLease, userDatabase);
      } catch (IOException ex) {
        throw new IllegalArgumentException("Error: ", ex);
      }
    }
  }

  private CharSequence getEncodedPassword(User u, String password) {
    return getEncryptedPassword(new EncryptionServiceSet() {
      @Override
      public byte[] getSalt() {
        return u.getSalt();
      }

      @Override
      public byte[] getInitializationVector() {
        return u.getInitializationVector();
      }

      @Override
      public byte[] getPassword() {
        return new byte[0];
      }

      @Override
      public EncryptionService getEncryptionService() {
        return null;
      }
    }, password);
  }

  @NonNull
  private Optional<? extends User> doAuthenticate(User u, String password) {
    synchronized (lock) {
      val encodedPassword = getEncodedPassword(u, password);
      val secret = vaultLease.lease(u.getId());
      if (Objects.equals(encodedPassword, ((StringSecret) secret.get()).getMaterial())) {
        u.setLastAuthenticated(new Date());
        return Optional.of(u);
      }
    }
    flush();
    return Optional.empty();
  }

  private CharSequence getEncryptedPassword(
      EncryptionServiceSet encryptionSet,
      String password
  ) {

    val pwdService = new JCAEncryptionService(
        encoding.encode(encryptionSet.getSalt()),
        encoding.encode(configuration.getPassword())
    );
    pwdService.setInitializationVector(encoding.encode(encryptionSet.getInitializationVector()));
    val secretKey = pwdService.generatePassword(password);
    return encoding.encode(secretKey.getEncoded());
  }

  private void updatePassword(User user, EncryptionServiceSet encryptionSet) {
    val secret = new StringSecret();
    secret.setId(user.getId());
    secret.setName("Password for " + user.getUsername());
    secret.setDescription("Password Secret");
    val password = getEncryptedPassword(encryptionSet, user.getPassword());
    secret.setMaterial(password);
    user.setPassword(password.toString());
    vaultLease.save(secret);
  }
}
