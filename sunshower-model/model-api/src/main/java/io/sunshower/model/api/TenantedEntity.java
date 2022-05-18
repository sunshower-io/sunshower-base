package io.sunshower.model.api;

import io.sunshower.persistence.id.Identifier;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class TenantedEntity extends AbstractEntity<Identifier> {


  @Setter
  @Getter(onMethod = @__({
      @OneToOne(fetch = FetchType.LAZY),
      @JoinColumn(name = "tenant_id")
  }))
  private Tenant tenant;


}
