package io.sunshower.realms.cryptkeeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.sunshower.arcus.lang.test.Tests;
import io.sunshower.crypt.core.LockedVaultException;
import io.sunshower.crypt.core.VaultException;
import io.sunshower.model.api.User;
import io.sunshower.model.api.UserDetails;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.UUID;
import lombok.NonNull;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileBackedCryptKeeperRealmTest {

  String password = "this_is_not_a_real_password";
  private FileBackedCryptKeeperRealm realm;

  @BeforeEach
  void setUp() {
    val file = new File(Tests.projectFile(), "build/" + UUID.randomUUID());
    assertTrue(file.mkdirs());
    this.realm = new FileBackedCryptKeeperRealm(new RealmConfiguration(file, password));
  }


  @Test
  void ensureRealmCanBeUnlocked() {
    assertFalse(realm.isLocked());
    realm.close();
    assertTrue(realm.isLocked());
  }

  @Test
  void ensureSavingUserWorks() {
    final User user = getUser();

    val id = realm.saveUser(user);
    val u = realm.getUser(id);
    assertNotNull(u);
    assertNotEquals("password", u.getPassword());
  }


  @Test
  void ensureAuthenticatingUserWorks() {
    val user = getUser();
    realm.saveUser(user);
    assertTrue(realm.authenticate(user.getUsername(), "password").isPresent());
  }

  @Test
  void ensureAddingUserResultsInUserBeingAdded() {
    val user = getUser();
    realm.saveUser(user);
    assertEquals(1, realm.getUsers().size());
  }

  @Test
  void ensureUserCannotBeRetrievedFromLockedVault() {
    val user = getUser();
    realm.saveUser(user);
    assertEquals(1, realm.getUsers().size());
    realm.close();
    assertThrows(LockedVaultException.class, () -> {
      realm.authenticate(user.getUsername(), "password");
    });
  }

  @Test
  void ensureRemovingUserFailsOnLockedVault() {
    val user = getUser();
    realm.saveUser(user);
    assertEquals(1, realm.getUsers().size());
    realm.lock();
    assertThrows(VaultException.class, () -> {
      realm.deleteUser(user);
    });
  }

  @Test
  void ensureRemovingUserWorksUponUnlock() {
    val user = getUser();
    realm.saveUser(user);
    assertEquals(1, realm.getUsers().size());
    realm.lock();
    realm.unlock(password);
    realm.deleteUser(user);
    assertEquals(0, realm.getUsers().size());
  }

  @Test
  void ensureRemovingUserWorks() {
    val user = getUser();
    realm.saveUser(user);
    assertEquals(1, realm.getUsers().size());
    realm.deleteUser(user);
    assertEquals(0, realm.getUsers().size());
  }

  @Test
  void ensureLockingAndUnlockingVaultWorks() {
    val user = getUser();
    realm.saveUser(user);
    assertEquals(1, realm.getUsers().size());
    realm.close();
    realm.unlock(password);
    assertTrue(realm.authenticate(user.getUsername(), "password").isPresent());
  }


  @Test
  void ensureNoSaltsAreReused() {

    for (int i = 0; i < 5; i++) {
      val u = getUser();
      u.setUsername(u.getUsername() + i);
      realm.saveUser(u);
    }

    val salts = new HashSet<String>();
    for (val user : realm.getUsers()) {
      salts.add(new String(user.getSalt(), StandardCharsets.UTF_8));
    }

    assertEquals(5, salts.size());
  }

  @Test
  void ensureNoIvsAreReused() {

    for (int i = 0; i < 5; i++) {
      val u = getUser();
      u.setUsername(u.getUsername() + i);
      realm.saveUser(u);
    }

    val salts = new HashSet<String>();
    for (val user : realm.getUsers()) {
      salts.add(new String(user.getInitializationVector(), StandardCharsets.UTF_8));
    }

    assertEquals(5, salts.size());
  }

  @NonNull
  private User getUser() {
    val user = new User();
    user.setUsername("Josiah");
    user.setPassword("password");

    val details = new UserDetails();
    details.setFirstName("Josiah");
    details.setLastName("Haswell");
    user.setDetails(details);
    return user;
  }

}