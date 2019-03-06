package io.sunshower.common.io;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class BytesTest {

  @Test
  public void ensureBytesCanReadFromInputStream() throws IOException {
    final ByteArrayInputStream bos = new ByteArrayInputStream("hello world!".getBytes());
    final byte[] data = Bytes.read(bos);
    assertThat(new String(data), is("hello world!"));
  }
}
