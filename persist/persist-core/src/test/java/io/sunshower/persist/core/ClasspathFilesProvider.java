package io.sunshower.persist.core;

import java.io.File;
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
    System.out.println("ENV: " + new File(ClassLoader.getSystemResource(".").getFile()).getAbsolutePath());
    return Paths.get(getClass().getResource("/application-2.yml").toURI());
//    return Collections.singleton(
//        Paths.get(ClasspathFilesProvider.class.getResource("application-2.yml").getFile()).toFile().toPath());
  }
}
