package io.sunshower.io;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import io.sunshower.encodings.Base58;
import java.io.IOException;
import java.io.StringReader;
import java.util.Random;
import lombok.val;
import org.junit.jupiter.api.Test;

class ReaderInputStreamTest {

  @Test
  void ensureReaderInputStreamWorksForEmptyInputStream() throws IOException {
    val read = new ReaderInputStream(new StringReader("")).read();
    assertThat(read, is(-1));
  }

  @Test
  void ensureReaderReadsAllCharactersCorrectlyForEmptyString() throws IOException {
    val s = new String(Files.read(new ReaderInputStream(new StringReader(""))));
    assertThat(s, is(""));
  }

  @Test
  void ensureReadingLargeStringWorks() throws IOException {
    val size = 1000;
    val randomBytes = new byte[size];
    new Random().nextBytes(randomBytes);
    val bytes = Base58.getInstance(Base58.Alphabets.Default).encode(randomBytes);
    val s = new String(Files.read(new ReaderInputStream(new StringReader(bytes))));
    assertThat(s, is(bytes));
  }
}
