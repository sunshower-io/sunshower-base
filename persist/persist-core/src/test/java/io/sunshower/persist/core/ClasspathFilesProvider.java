package io.sunshower.persist.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import lombok.SneakyThrows;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;

public class ClasspathFilesProvider implements ConfigFilesProvider {
  public ClasspathFilesProvider(String s) {}

  @Override
  @SneakyThrows
  public Iterable<Path> getConfigFiles() {
    return Paths.get(ClassLoader.getSystemResource("application-2.yml").toURI());
//    return Collections.singleton(
//        Paths.get(ClasspathFilesProvider.class.getResource("application-2.yml").getFile()).toFile().toPath());
  }
}
