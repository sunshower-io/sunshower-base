package io.sunshower.model.api;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class AbstractEntity<ID extends Serializable> implements Persistable<ID> {

  @Setter
  @Getter(onMethod = @__({@Id, @GeneratedValue(generator = "flake")}))
  private ID id;

  protected AbstractEntity() {
  }

  protected AbstractEntity(ID id) {
    this.id = id;
  }

  @Override
  public Persistable<ID> clone() {
    return new AbstractEntity<>(id);
  }


  @Override
  public int hashCode() {
    return isNew() ? 0 : getId().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }

    if (getClass().isAssignableFrom(o.getClass())) {
      return ((AbstractEntity<ID>) o).getId().equals(id);
    }
    return false;

  }
}
