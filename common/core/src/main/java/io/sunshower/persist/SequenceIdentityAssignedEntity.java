package io.sunshower.persist;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;


public class SequenceIdentityAssignedEntity<
        ID extends Serializable
        > extends PersistentEntity<ID>
        implements SequenceIdentityAssigned<ID>
{

    @Id
    private ID id;

    @Transient
    private final Sequence<ID> sequence;


    protected SequenceIdentityAssignedEntity(
            Sequence<ID> sequence
    ) {
        super(sequence.next());
        this.sequence = sequence;
    }


    @Override
    public Sequence<ID> getSequence() {
        return sequence;
    }

    @Override
    public ID getId() {
        return null;
    }

    @Override
    protected void setId(ID id) {

    }

}
