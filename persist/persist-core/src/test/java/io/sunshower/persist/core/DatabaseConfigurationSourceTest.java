package io.sunshower.persist.core;

import static io.sunshower.persist.core.DataSourceConfigurations.isBaseline;
import static io.sunshower.persist.core.DataSourceConfigurations.validate;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import io.sunshower.test.common.TestClasspath;
import io.sunshower.test.common.TestConfigurationConfiguration;
import javax.inject.Inject;
import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.classpath.ClasspathConfigurationSource;
import org.cfg4j.source.context.environment.DefaultEnvironment;
import org.cfg4j.source.context.environment.Environment;
import org.cfg4j.source.context.environment.ImmutableEnvironment;
import org.cfg4j.source.files.FilesConfigurationSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
  classes = {DataSourceConfiguration.class, TestConfigurationConfiguration.class}
)
public class DatabaseConfigurationSourceTest {

  @Inject private DatabaseConfigurationSource source;

  @Test
  public void ensureJdbcIsRead() {
    ClasspathConfigurationSource s = new ClasspathConfigurationSource();
    ConfigurationProvider p =
        new ConfigurationProviderBuilder()
            .withConfigurationSource(s)
            .withEnvironment(new ImmutableEnvironment("application-2.yaml"))
            .build();
    DatabaseConfigurationSource jdbc = p.bind("jdbc", DatabaseConfigurationSource.class);
    assertThat(jdbc.jndiPath(), is("coolbeans"));
  }


  @Test
  public void ensureBaselineIsRead() {
    assertThat(isBaseline(source), is(false));
  }

  @Test
  public void ensureBaselineVersionIsRead() {
    assertThat(source.version(), is("-1"));
  }

  @Test
  public void ensureValidationWorks() {
    validate(source);
  }
}
