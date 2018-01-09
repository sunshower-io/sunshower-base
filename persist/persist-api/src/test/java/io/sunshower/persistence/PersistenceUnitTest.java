package io.sunshower.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class PersistenceUnitTest {

  @Test
  public void ensureParseLocationDoesntChangeHardcodedValue() {
    String location = PersistenceUnit.parseLocation("classpath:h2", Dialect.Postgres);
    assertThat(location, is("classpath:h2"));
  }

  @Test
  public void ensurePersistenceUnitCreatesCorrectPathForPostgres() {
    String location = PersistenceUnit.parseLocation("classpath:{dialect}", Dialect.Postgres);
    assertThat(location, is("classpath:postgres"));
  }
}
