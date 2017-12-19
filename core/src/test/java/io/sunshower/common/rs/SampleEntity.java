package io.sunshower.common.rs;

import io.sunshower.common.Identifier;
import io.sunshower.persist.Identifiers;
import org.opensky.Definition;

import javax.xml.bind.annotation.*;

/**
 * Created by haswell on 4/7/17.
 */

@XmlRootElement(
        name = "sample-entity"
)
@Definition(
        root ="misc",
        type = "sample-entity",
        organization = "io.sunshower"
)
public class SampleEntity {

    @XmlID
    @XmlAttribute
    private Identifier id;

    public SampleEntity() {
        id = Identifiers.newSequence().next();
    }

}
