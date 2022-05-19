package io.sunshower.model.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.sunshower.arcus.condensation.Condensation;
import io.sunshower.crypt.JCAEncryptionService;
import io.sunshower.lang.common.encodings.Encodings;
import io.sunshower.lang.common.encodings.Encodings.Type;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.val;
import org.junit.jupiter.api.Test;

@ModelTest
class UserTest {

  @PersistenceContext private EntityManager entityManager;

  @Test
  void ensureUserIsWrittenCorrectly() throws IOException {
    val user = new User();

    user.setUsername("Josiah");

    val encoding = Encodings.create(Type.Base58);
    val salt = generate(32);
    val iv = generate(16);
    val encryptionService =
        new JCAEncryptionService(encoding, encoding.encode(salt.array()), "testpassword");
    encryptionService.setInitializationVector(encoding.encode(iv.array()));

    user.setSalt(salt.array());
    user.setInitializationVector(iv.array());
    val pw = encryptionService.generatePassword("testpassword");
    val pw2 = encoding.encode(pw.getEncoded());
    user.setPassword(pw2);

    val details = new UserDetails();
    details.setLastName("test");
    details.setFirstName("whatever");
    user.setDetails(details);
    entityManager.persist(user);
    entityManager.flush();
    val result = Condensation.create("json").write(User.class, user);
    System.out.println(result);
  }

  @Test
  void ensureSavingPasswordAndIVAndSaltWorks() {
    val user = new User();

    user.setUsername("Josiah");

    val encoding = Encodings.create(Type.Base58);
    val salt = generate(32);
    val iv = generate(16);
    val encryptionService =
        new JCAEncryptionService(encoding, encoding.encode(salt.array()), "testpassword");
    encryptionService.setInitializationVector(encoding.encode(iv.array()));

    user.setSalt(salt.array());
    user.setInitializationVector(iv.array());
    val pw = encryptionService.generatePassword("testpassword");
    val pw2 = encoding.encode(pw.getEncoded());
    user.setPassword(pw2);
    entityManager.persist(user);
    entityManager.flush();

    val encryptionService2 =
        new JCAEncryptionService(encoding, encoding.encode(user.getSalt()), "testpassword");
    encryptionService2.setInitializationVector(encoding.encode(user.getInitializationVector()));

    val encoded = encoding.encode(encryptionService2.generatePassword("testpassword").getEncoded());
    assertEquals(encoded, user.getPassword());
  }

  @Test
  void ensureUserDetailsIsCascaded() {
    val user = new User();
    user.setUsername("josiah");
    user.setPassword("password!");

    val details = new UserDetails();
    user.setDetails(details);

    details.setFirstName("Josiah");
    details.setLastName("Haswell");

    entityManager.persist(user);
    entityManager.flush();

    assertNotNull(details.getId());
    assertEquals(details.getId(), user.getId());
  }

  private ByteBuffer generate(int i) {
    val random = new byte[i];
    new SecureRandom().nextBytes(random);
    return ByteBuffer.wrap(random);
  }

  private byte[] decode(CharBuffer buffer, int expectedDecodedSize) {
    val bb = StandardCharsets.UTF_8.encode(buffer);
    val ba = bb.array();
    if (ba.length != expectedDecodedSize) {
      throw new IllegalStateException(
          String.format("Error: expected %d, got %d", ba.length, expectedDecodedSize));
    }
    return ba;
  }
}
