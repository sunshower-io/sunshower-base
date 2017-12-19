package io.sunshower.persistence;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by haswell on 5/2/17.
 */
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