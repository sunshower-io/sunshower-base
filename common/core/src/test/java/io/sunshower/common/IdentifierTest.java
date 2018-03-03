package io.sunshower.common;

import io.sunshower.encodings.Base58;
import io.sunshower.encodings.Encoding;
import io.sunshower.persist.Identifiers;
import io.sunshower.persist.Sequence;
import io.sunshower.test.common.SerializationAware;
import io.sunshower.test.common.SerializationTestCase;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitPlatform.class)
public class IdentifierTest extends SerializationTestCase {

  final Sequence<Identifier> sequence = Identifiers.newSequence();

  public IdentifierTest() {
    super(SerializationAware.Format.JSON, new Class[] {Identifier.class});
  }

  @Test
  public void ensureIdIsExpected() {

    Identifier id = Identifier.valueOf(UUID.fromString("015cd8ef-54c9-db4d-e85d-f122d25639b7"));
    System.out.println(id);
  }

  @Test
  public void ensureUUIDGeneratedIsCorrect() {
    Identifier id = Identifier.valueOf(UUID.randomUUID());
  }

  @Test
  public void ensureTenantIdsWork() {
    String[] ids = {"b12459b6-77b7-4b3a-8e6a-efe714cbf8b6"};
    Identifier id = Identifier.valueOf(UUID.fromString(ids[0]));
  }

  @Test
  public void ensureBase58RegexWorks() {
    assertTrue(Identifier.isIdentifier("VVPfiH6JEvpAsFvpKTwunB"));
  }

  @Test
  public void ensureIdentifierWorksInSets() {
    Identifier id1 = next();
    Set<Identifier> ids = new HashSet<>();
    assertTrue(ids.add(id1));

    Identifier clone = Identifier.decode(id1.toString());
    assertThat(ids.contains(clone), is(true));
    assertThat(ids.remove(clone), is(true));
    assertThat(ids.contains(clone), is(false));
  }

  @Test
  public void ensureIdentifierValueIsCopiedCorrectly() {
    Identifier random = next();
    assertThat(Identifier.decode(random.toString()), is(random));
  }

  @Test
  public void ensureSerializingToJsonProducesExpectedValue() {

    final String data = "{\"id\":\"VVPfiH6JEvpAsFvpKTwunB\"}";
    assertThat(read(data, Identifier.class), is(Identifier.valueOf("VVPfiH6JEvpAsFvpKTwunB")));
    write(next(), System.out);
  }

  @Test
  public void ensureCopyingDataAcrossXmlProducesExpectedResults() {
    setFormat(SerializationAware.Format.XML);

    write(next(), System.out);
  }

  @Test
  public void ensureEncodedGeneratedIdsWork() {

    Sequence<Identifier> ids = Identifiers.newSequence(false);
    long t1 = System.currentTimeMillis();
    for (int i = 0; i < 100000; i++) {
      String id = ids.next().toString();
      String encoded = Identifier.valueOf(id).toString();
      Identifier decode = Identifier.decode(encoded);
    }
    long t2 = System.currentTimeMillis();
    System.out.format("Time per operation: %d\n", (t2 - t1));
  }

  @Test
  public void timeEncodeDecodeOperations() {
    long t1 = System.currentTimeMillis();
    for (int i = 0; i < 100000; i++) {
      UUID id = UUID.randomUUID();
      String encoded = Identifier.valueOf(id).toString();
      Identifier decode = Identifier.decode(encoded);
    }
    long t2 = System.currentTimeMillis();
    System.out.format("Time per operation: %d\n", (t2 - t1));
  }

  @Test
  public void ensureEncodingAndDecodingIsIdempotent() {
    String value = "6Rwk8SVHrW6CQy4LLsfjBw";
    assertThat(Identifier.valueOf(value).toString(), is(value));
  }

  @Test
  public void ensureDecodingAndEncodingWorks() {

    String value = "6Rwk8SVHrW6CQy4LLsfjBw";
    final Encoding encoding = Base58.getInstance(Base58.Alphabets.Default);
    String encode = encoding.encode(encoding.decode(value));
    assertThat(value, is(encode));
  }

  private Identifier next() {
    return sequence.next();
  }
}
