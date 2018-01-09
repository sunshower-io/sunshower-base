package io.sunshower.common.rs;

import io.sunshower.common.Identifier;
import io.sunshower.persist.Identifiers;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "sample-entity")
public class SampleEntity {

  @XmlID @XmlAttribute private Identifier id;

  public SampleEntity() {
    id = Identifiers.newSequence().next();
  }
}
