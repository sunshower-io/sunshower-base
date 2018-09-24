package io.sunshower.test.persist;

import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import persist.test.TestService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class EnableSecurityTest {

  @Inject private TestService service;

  @Test
  @WithUserDetails("administrator")
  public void ensureAuthenticatedServiceWorks() {
    service.save();
  }
}
