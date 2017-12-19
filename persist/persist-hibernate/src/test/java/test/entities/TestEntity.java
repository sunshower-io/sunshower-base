package test.entities;

import io.sunshower.persistence.core.DistributableEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by haswell on 11/16/16.
 */
@Entity
@Table(name = "TestEntity")
public class TestEntity extends DistributableEntity {

    @ManyToOne
    @JoinColumns({
            @JoinColumn(
                    name = "parent_id",
                    insertable = false,
                    updatable = false
            )
    })
    private TestEntity parent;

    @OneToMany(
            mappedBy = "parent",
            cascade = CascadeType.ALL
    )
    private Set<TestEntity> children;

    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestEntity getParent() {
        return parent;
    }

    public Set<TestEntity> getChildren() {
        return children;
    }



    public void addChild(TestEntity entity) {
        if(this.children == null) {
            this.children = new HashSet<>();
        }
        entity.parent = this;
        this.children.add(entity);
    }

    public void setParent(TestEntity parent) {
        this.parent = parent;
    }

    public void setChildren(Set<TestEntity> children) {
        this.children = children;
    }


}
