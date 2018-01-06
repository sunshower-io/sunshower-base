package io.sunshower.persistence.core;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import io.sunshower.common.Identifier;
import io.sunshower.persist.Sequence;
import io.sunshower.persist.internal.jaxb.Base58ByteArrayConverter;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.FieldBridge;


@MappedSuperclass
@IdClass(Identifier.class)
@XmlRootElement(name = "entity")
public class DistributableEntity extends AbstractEntity<Identifier> {
    
    public static final Sequence<Identifier> sequence = 
            Entities.getDefaultFlakeSequence();
    
    

    @Id
    @XmlID
    @DocumentId
    @Column(name = "id")
    @XmlAttribute(name = "id")
    @FieldBridge(impl = IdentifierBridge.class)
    @XmlJavaTypeAdapter(Base58ByteArrayConverter.class)
    private byte[] id;
    


    public DistributableEntity() {
        super(null);
        setId(getSequence().next());
    }

    public DistributableEntity(Identifier id) {
        super(id);
    }

    @Override
    public Identifier getId() {
        return Identifier.valueOf(id);
    }


    @Override
    public void setId(Identifier id) {
        if(id != null) {
            this.id = io.sunshower.common.Identifiers.getBytes(id);
        }
    }

    @Override
    public Identifier getIdentifier() {
        return getId();
    }

    @Override
    public Sequence<Identifier> getSequence() {
        return sequence;
    }
}
