package io.sunshower.test.ws.cfg;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "test")
public class TestEntity {
   
    @XmlAttribute
    String name;
    
    protected TestEntity() {
        
    }
    
    public TestEntity(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
