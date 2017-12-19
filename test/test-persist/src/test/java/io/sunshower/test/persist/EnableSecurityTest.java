package io.sunshower.test.persist;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import persist.test.TestService;

import javax.inject.Inject;

/**
 * Created by haswell on 3/29/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfiguration.class)
public class EnableSecurityTest {

    @Inject
    private TestService service;

    @Test
    @WithUserDetails("administrator")
    public void ensureAuthenticatedServiceWorks() {
        service.save();
    }
}
