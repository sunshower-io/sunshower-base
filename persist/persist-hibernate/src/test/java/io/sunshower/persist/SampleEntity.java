package io.sunshower.persist;

import io.sunshower.persistence.core.DistributableEntity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by haswell on 2/28/17.
 */
@XmlRootElement
public class SampleEntity extends DistributableEntity {
    @XmlAttribute
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
