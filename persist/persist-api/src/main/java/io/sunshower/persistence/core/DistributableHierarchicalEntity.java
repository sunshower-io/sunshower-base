package io.sunshower.persistence.core;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import io.sunshower.common.Identifier;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.FieldBridge;

/**
 * Created by haswell on 3/13/17.
 */
@MappedSuperclass
@IdClass(Identifier.class)
@XmlRootElement(name = "hierarchical-entity")
public abstract class DistributableHierarchicalEntity<T extends Hierarchical<Identifier, T>>
        extends HierarchichalEntity<Identifier, T> {


    @Id
    @DocumentId
    @Column(name = "id")
    @XmlAttribute(name = "id")
    @FieldBridge(impl = ByteArrayBridge.class)
    private byte[] id;


    protected DistributableHierarchicalEntity(Identifier id) {
        super(id);
    }
    
    protected DistributableHierarchicalEntity() {
        this(DistributableEntity.sequence.next());
    }

    @Override
    public Identifier getIdentifier() {
        return getId();
    }

    @Override
    public Identifier getId() {
        return id == null ? null : Identifier.valueOf(id) ;
    }

    @Override
    public void setId(Identifier id) {
        if(id != null) {
            this.id = id.value();
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{" +
                "id=" + id +
                '}';
    }
}
