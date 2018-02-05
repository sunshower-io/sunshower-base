package io.sunshower.persist.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;

public class ClasspathFilesProvider implements ConfigFilesProvider {
  public ClasspathFilesProvider(String s) {}

  @Override
  public Iterable<Path> getConfigFiles() {
    return Collections.singleton(
        Paths.get(ClassLoader.getSystemResource("application-2.yml").getFile()).toFile().toPath());
  }
}
