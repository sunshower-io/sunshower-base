package io.sunshower.persist;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PersistentEntity<ID extends Serializable> implements Persistable<ID> {

  protected PersistentEntity(ID id) {
    setId(id);
  }

  protected abstract void setId(ID id);

  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PersistentEntity)) return false;

    PersistentEntity<?> that = (PersistentEntity<?>) o;
    final ID id = getId();
    final ID thatId = (ID) that.getId();

    return id != null ? id.equals(thatId) : thatId == null;
  }

  @Override
  public int hashCode() {
    final ID id = getId();
    return id != null ? id.hashCode() : 0;
  }
}
