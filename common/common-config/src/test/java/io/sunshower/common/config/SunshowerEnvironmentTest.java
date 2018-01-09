package io.sunshower.common.config;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import io.sunshower.test.common.TestClasspath;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.powermock.api.mockito.PowerMockito;

public class SunshowerEnvironmentTest {

  static final Map<String, String> env;

  static {
    env = new HashMap<>();
    PowerMockito.mockStatic(System.class);
    given(System.getenv()).willReturn(env);
  }

  @Test
  public void ensureSunshowerHomeResolvedFromPropertyReturnsCorrectResult() throws IOException {
    File homeDir = TestClasspath.getOrCreateDirectoryInBuildDirectory(UUID.randomUUID().toString());
    System.setProperty("sunshower.home", homeDir.getAbsolutePath());
    assertThat(SunshowerEnvironment.getSunshowerHome(), is(homeDir));
  }

  @Test
  public void ensureSunshowerHomeResolvedFromEnvWorks() throws IOException {
    String path = TestClasspath.getOrCreateDirectoryInBuildDirectory("sunshower").getAbsolutePath();
    env.put(SunshowerEnvironment.SUNSHOWER_HOME_ENV_KEY, path);

    assertThat(SunshowerEnvironment.getSunshowerHome(), is(path));
  }
}
