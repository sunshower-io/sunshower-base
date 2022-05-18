package io.sunshower.model.api;

import java.io.Serializable;
import javax.annotation.Nullable;

/**
 * a persistable is an object that can be persisted by a Sunshower subsystem (usually JPA, but not
 * necessarily)
 *
 * @param <ID> the type of the ID
 */
@SuppressWarnings("PMD")
public interface Persistable<ID extends Serializable> extends Cloneable {

  /** @return the ID associated with this persistable, or null if it hasn't been persisted yet */
  @Nullable
  ID getId();

  /** @return true if this persistable has not been persisted yet */
  default boolean isNew() {
    return getId() != null;
  }

  /** @return a clone of this persistable */
  Persistable<ID> clone();
}
