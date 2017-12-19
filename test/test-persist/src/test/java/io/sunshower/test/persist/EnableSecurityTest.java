package io.sunshower.test.persist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import persist.test.TestService;

import javax.inject.Inject;


@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
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
