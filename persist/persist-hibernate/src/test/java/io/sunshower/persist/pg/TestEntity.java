package io.sunshower.persist.pg;

import javax.persistence.*;

/**
 * Created by haswell on 5/3/17.
 */
@Entity
@Table(name = "FRAP")
public class TestEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
