package test.entities;

import io.sunshower.persistence.core.DistributableEntity;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by haswell on 3/3/17.
 */
@Entity
@Indexed(index = "entity")
@Table(name = "INDEXED_ENTITY")
public class IndexedEntity extends DistributableEntity {



    @Field
    @Basic
    private String name;

    @Basic
    @Field
    private String text;

    @Basic
    @Field
    private String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
