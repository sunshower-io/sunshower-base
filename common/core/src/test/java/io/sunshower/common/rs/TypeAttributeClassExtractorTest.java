package io.sunshower.common.rs;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import io.sunshower.test.common.SerializationAware;
import io.sunshower.test.common.SerializationTestCase;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.oxm.annotations.XmlClassExtractor;
import org.eclipse.persistence.oxm.annotations.XmlValueExtension;
import org.junit.jupiter.api.Test;

class TypeAttributeClassExtractorTest extends SerializationTestCase {

  public TypeAttributeClassExtractorTest() {
    super(SerializationAware.Format.JSON, new Class<?>[] {SampleElement.class});
  }

  @Test
  public void ensureSampleElementIsWrittenCorrectlyAndLongApplies() {
    final SampleElement e = new SampleElement();
    e.value = 1L;
    assertThat(copy(e).value, is(1L));
  }

  @Test
  public void ensureSampleElementIsWrittenCorrectlyAndStringApplies() {
    final SampleElement e = new SampleElement();
    e.value2 = "coolio";
    assertThat(copy(e).value2, is("coolio"));
  }

  @Test
  public void ensureSampleElementIsWrittenCorrectlyAndIntApplies() {
    final SampleElement e = new SampleElement();
    e.value3 = 34;
    assertThat(copy(e).value3, is(34));
  }

  @XmlRootElement
  @XmlClassExtractor(TypeAttributeClassExtractor.class)
  static final class SampleElement {
    @XmlAttribute private String type = SampleElement.class.getName();

    @XmlValueExtension private long value;
    @XmlValueExtension private int value3;

    @XmlValueExtension private String value2;
  }
}
