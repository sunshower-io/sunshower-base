package io.sunshower.test.common;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import javax.inject.Inject;
import javax.inject.Named;
import org.cfg4j.provider.ConfigurationProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfigurationConfiguration.class)
public class TestConfigurationConfigurationTest {

  @Inject private ConfigurationProvider provider;

  @Inject
  @Named(TestConfigurations.TEST_CONFIGURATION_REPOSITORY_PATH)
  private String location;

  @Test
  public void ensureConfigurationLocationExists() {
    assertTrue(new File(location).exists());
  }

  @Test
  public void ensureProviderIsInjected() {
    assertThat(provider, is(not(nullValue())));
  }

  @Test
  public void ensureConfigurationIsRead() {
    assertThat(provider.bind("jdbc", JDBC.class).username(), is("sa"));
  }
}
