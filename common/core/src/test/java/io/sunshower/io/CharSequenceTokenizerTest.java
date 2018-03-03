package io.sunshower.io;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

class CharSequenceTokenizerTest {

  @Test
  public void ensureReadingFromStreamWorks() {
    String input = "hello world!";
    long expectedSize = input.length();
    long size = IO.tokenize(new StringReader(input)).stream().count();
    assertThat(expectedSize, is(size));
  }

  @Test
  public void ensureReadingWorks() {
    String input = "hello world!";
    long expectedSize = input.length();

    long size = IO.tokenize(input).stream().count();
    assertThat(expectedSize, is(size));
  }

  @Test
  public void ensureAdvanceWorksCorrectly() {

    String input = "h";
    CharacterSequence cs = new CharacterSequenceSequence(input);
    assertThat(cs.peek(), is('h'));
    assertThat(cs.hasNext(), is(true));
    assertThat(cs.peek(), is('h'));
    assertThat(cs.next(), is('h'));
    assertThat(cs.hasNext(), is(false));
  }

  @Test
  public void ensurePeekingReadsWithoutAdvancing() {
    String input = "hello world!";
    CharacterTokenizer tokenize = IO.tokenize(input);
    assertThat(tokenize.peek(), is('h'));
    assertThat(tokenize.peek(), is('h'));
    assertThat(tokenize.read(), is('h'));
    assertThat(tokenize.read(), is('e'));
  }

  @Test
  public void ensureUnreadUnreads() {
    String input = "hello world!";
    CharacterTokenizer tokenize = IO.tokenize(input);
    assertThat(tokenize.peek(), is('h'));
    tokenize.push('v');
    assertThat(tokenize.peek(), is('v'));
    assertThat(tokenize.read(), is('v'));
    assertThat(tokenize.read(), is('h'));
  }

  @Test
  public void ensureReadingProducesExpectedResults() {
    String input = "hello world!";
    String output =
        IO.tokenize(input)
            .stream()
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    assertThat(input, is(output));
  }

  @Test
  public void ensureCRsAreConvertedToLfs() {

    String input = "hello \r\n world!";
    String output =
        IO.tokenize(input)
            .stream()
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    String expected = "hello \n world!";
    assertThat(output, is(expected));
  }
}
