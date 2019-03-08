package io.sunshower.persist;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import io.sunshower.common.Identifier;
import io.sunshower.test.common.SerializationAware;
import io.sunshower.test.common.SerializationTestCase;
import java.time.Clock;
import java.util.*;
import org.junit.jupiter.api.Test;

public class IdentifierTest extends SerializationTestCase {

  public IdentifierTest() {
    super(SerializationAware.Format.JSON, new Class<?>[] {Identifier.class});
  }

  @Test
  public void ensureWhateverWorks() {
    Sequence<Identifier> identifierSequence = Identifiers.newSequence();
    Identifier fst = identifierSequence.next();
    System.out.println(fst);
    System.out.println(identifierSequence.next());
  }

  @Test
  public void ensureValueOfValidIdentifierReturnsCorrectIdentifier() {
    Identifier fst = Identifiers.newSequence().next();
    assertThat(Identifier.valueOf(fst.toString()), is(fst));
  }

  @Test
  public void ensureIdentifierCanBeUsedAsKey() {
    Identifier fst = Identifiers.newSequence().next();
    Identifier snd = Identifier.valueOf(fst.toString());

    Map<Identifier, String> values = new HashMap<>();
    values.put(fst, "Cool");

    assertThat(values.get(snd), is("Cool"));
  }

  @Test
  public void ensureIdentifierIsSerializedCorrectly() {
    Identifier id = Identifiers.newSequence().next();
    assertThat(copy(id), is(id));
  }

  @Test
  public void ensureApplyingBackpressureWorks() {

    final Set<Identifier> ids = new HashSet<>();
    Sequence<Identifier> seq = Identifiers.newSequence();
    for (int i = 0; i < 1500; i++) {
      ids.add(seq.next());
    }

    assertThat(ids.size(), is(1500));
  }

  @Test
  public void ensureIdentifierSequenceGeneratesIdsApproximatelyInSequence() {
    Sequence<Identifier> seq = Identifiers.newSequence(Clock.systemUTC(), true);
    int swaps = 0;
    for (int j = 0; j < 20; j++) {
      final List<Identifier> ids = new ArrayList<>();

      for (int i = 0; i < 20; i++) {
        ids.add(seq.next());
      }

      final List<Identifier> copy = new ArrayList<>(ids);
      Collections.sort(ids, (lhs, rhs) -> -lhs.compareTo(rhs));

      final Iterator<Identifier> iiter = ids.iterator();
      final Iterator<Identifier> citer = copy.iterator();

      for (; ; ) {
        if (citer.hasNext() && iiter.hasNext()) {
          Identifier fst = citer.next();
          Identifier snd = iiter.next();
          if (!fst.equals(snd)) {
            swaps++;
          }
        } else {
          break;
        }
      }
    }
    assertThat(swaps / 20 > 10, is(true));
  }
}
