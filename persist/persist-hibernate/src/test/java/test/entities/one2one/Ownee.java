package test.entities.one2one;

import io.sunshower.common.Identifier;
import io.sunshower.persistence.core.DistributableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by haswell on 2/24/17.
 */
@Entity
@Table(name = "OWNEE")
@Access(AccessType.FIELD)
public class Ownee extends DistributableEntity {




    @Basic
    @NotNull
    private String name;

    @OneToOne(mappedBy = "ownee")
    private Owner owner;


    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
