package io.sunshower.test.common;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class TestClasspathTest {

  @Test
  public void ensureBuildDirResolvesBuildDir() {
    assertThat(TestClasspath.buildDir().isDirectory(), is(true));
  }

  @Test
  public void ensureBuildDirResolvesFile() throws IOException {
    File file = TestClasspath.getOrCreateFileInBuildDirectory("hello.txt");
    FileWriter fileWriter = new FileWriter(file);
    fileWriter.write("hello");
    fileWriter.close();
    assertThat(file.exists(), is(true));
    assertThat(file.isFile(), is(true));
  }

  @Test
  public void ensureFileDirResolvesFile() throws IOException {
    File file = TestClasspath.getOrCreateDirectoryInBuildDirectory("hello");
    assertThat(file.isDirectory(), is(true));
    assertThat(file.exists(), is(true));
  }
}
