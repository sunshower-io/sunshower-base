package io.sunshower.test.common;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

public class TestClasspath {

  public static File buildDir() {
    final File file = new File(ClassLoader.getSystemResource(".").getFile());
    for (File f = file; f != null; f = f.getParentFile()) {
      File build = new File(f, "build");
      if (build.exists() && build.isDirectory()) {
        return build;
      }
      build = new File(f, "out");
      if (build.exists() && build.isDirectory()) {
        return build;
      }
    }
    throw new NoSuchElementException("Failed to resolve build directory");
  }



  public static File getOrCreateDirectoryInBuildDirectory(String path) throws IOException {
    File file = new File(buildDir(), path);
    if (file.mkdirs()) {
      return file;
    }
    throw new IllegalStateException(
        String.format("Failed to create directory %s in %s", path, buildDir()));
  }

  public static File getOrCreateFileInBuildDirectory(String path) throws IOException {
    File file = new File(buildDir(), path);
    if (file.createNewFile()) {
      return file;
    }
    throw new IllegalStateException(
        String.format("Failed to create file %s in %s", path, buildDir()));
  }
}
