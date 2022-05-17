package io.sunshower.model.api;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.val;
import org.junit.jupiter.api.Test;

@ModelTest
class UserTest {

  @PersistenceContext private EntityManager entityManager;

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

}