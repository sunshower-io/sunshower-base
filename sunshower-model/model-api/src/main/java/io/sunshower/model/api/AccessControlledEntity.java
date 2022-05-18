package io.sunshower.model.api;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessControlledEntity<ID extends Serializable> extends AbstractEntity<ID> {

  /** the ACL identity for this entity */
  @Getter(
      onMethod =
          @__({
            @OneToOne(fetch = FetchType.LAZY),
            @JoinColumn(name = "id", insertable = false, updatable = false)
          }))
  private ObjectIdentity identity;
}
