package test.entities.one2many;

import javax.inject.Named;
import javax.persistence.*;
import java.util.UUID;

/**
 * Created by haswell on 2/24/17.
 */
@Entity
@Table(name = "one2many_owned")
public class Owned {


    @Id
    private UUID id;

    @ManyToOne
    private Owner owner;

    public Owned() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
