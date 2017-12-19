package io.sunshower.persistence.core;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by haswell on 2/25/17.
 */
@MappedSuperclass
public abstract class AbstractEntity<T extends Serializable> implements Persistable<T> {

    protected AbstractEntity(T id) {
        setId(id);
        setDefaults();
    }

    protected void setDefaults() {

    }





    public abstract T getId();



    public abstract void setId(T id);

    public int hashCode() {
        return getId().hashCode();
    }

    public boolean equals(Object o) {
        if(o == this) return true;
        if(o == null) return false;
        if(o.getClass().isAssignableFrom(getClass())) {
            return ((Persistable) o).getId().equals(getId());
        }
        return false;
    }




}
