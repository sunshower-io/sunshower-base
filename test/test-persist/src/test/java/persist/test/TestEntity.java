package persist.test;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "TEST_ENTITY")
public class TestEntity {

    @Id
    private UUID id;


    @Basic
    @Column(name = "first_name")
    private String firstName;

    public TestEntity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
