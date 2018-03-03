package io.sunshower.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.sunshower.encodings.Base58;
import java.io.StringReader;
import java.util.Random;
import org.junit.jupiter.api.Test;

class ReaderCharacterSequenceTest {

  char[] alphabet = Base58.Alphabets.Default.getAlphabet();

  @Test
  public void ensureReaderWorksWithMultipleOverflow() {
    String random = generateRandom(8192 * 3 + 15);
    StringReader reader = new StringReader(random);
    CharacterSequence seq = new ReaderCharacterSequence(reader);
    StringBuilder b = new StringBuilder();
    while (seq.hasNext()) {
      b.append(seq.next());
    }
    assertEquals(b.toString(), random);
  }

  @Test
  public void ensureReaderWorksWithOverflow() {
    String random = generateRandom(8195);
    StringReader reader = new StringReader(random);
    CharacterSequence seq = new ReaderCharacterSequence(reader);
    StringBuilder b = new StringBuilder();
    while (seq.hasNext()) {
      b.append(seq.next());
    }
    assertEquals(b.toString(), random);
  }

  @Test
  public void ensureReaderWorks() {
    String random = generateRandom(8191);
    StringReader reader = new StringReader(random);
    CharacterSequence seq = new ReaderCharacterSequence(reader);
    StringBuilder b = new StringBuilder();
    while (seq.hasNext()) {
      b.append(seq.next());
    }
    assertEquals(b.toString(), random);
  }

  private String generateRandom(int i) {
    StringBuilder b = new StringBuilder(i);
    Random rand = new Random();
    for (int j = 0; j < i; j++) {
      b.append(alphabet[(rand.nextInt(100) * j) % alphabet.length]);
    }
    return b.toString();
  }
}
