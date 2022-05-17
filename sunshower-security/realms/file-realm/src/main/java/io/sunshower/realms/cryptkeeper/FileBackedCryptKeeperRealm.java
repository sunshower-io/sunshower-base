package io.sunshower.realms.cryptkeeper;

import io.sunshower.arcus.condensation.Condensation;
import io.sunshower.crypt.DefaultSecretService;
import io.sunshower.crypt.core.SecretService;
import io.sunshower.lang.tuple.Pair;
import io.sunshower.realms.RealmManager;
import java.io.File;
import lombok.NonNull;
import lombok.val;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class FileBackedCryptKeeperRealm extends AbstractUserDetailsAuthenticationProvider implements
    RealmManager {


  private final Condensation condensation;
  private final SecretService secretService;

  public FileBackedCryptKeeperRealm(@NonNull SecretService secretService,
      @NonNull Condensation condensation) {
    this.condensation = condensation;
    this.secretService = secretService;
  }

  public FileBackedCryptKeeperRealm(File base) {
    this.condensation = Condensation.create("json");
    this.secretService = new DefaultSecretService(base, condensation);
  }

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

  }

  @Override
  protected UserDetails retrieveUser(String username,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    return null;
  }
}
