package io.sunshower.realms.cryptkeeper;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.security.SecureRandom;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Getter
@Setter
@SuppressFBWarnings
public class RealmConfiguration {

  private final File base;
  private final CharSequence password;
  private byte[] salt;
  private byte[] initializationVector;

  public RealmConfiguration(
      File base, CharSequence password, byte[] salt, byte[] initializationVector) {
    this.base = base;
    this.password = password;
    this.salt = salt;
    this.initializationVector = initializationVector;
  }

  public RealmConfiguration(File base, CharSequence password) {
    this(base, password, generate(32), generate(16));
  }

  static final byte[] generate(int length) {
    val result = new byte[length];
    new SecureRandom().nextBytes(result);
    return result;
  }
}
