package io.sunshower.model.api;

import static io.sunshower.model.api.SecurityTables.ACCESS_CONTROL_ENTRY;

import io.sunshower.persistence.id.Identifier;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ACCESS_CONTROL_ENTRY)
public class AccessControlEntry extends AbstractEntity<Identifier> {


  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = "ace_order")
  }))
  private int order;


  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = "mask")
  }))
  private int mask;


  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = "granting")
  }))
  private boolean granting;

  @Setter
  @Getter(onMethod = @__({
      @OneToOne,
      @JoinColumn(name = "sid")
  }))
  private SecurityIdentity identity;


  @Setter
  @Getter(onMethod = @__({
      @OneToOne(fetch = FetchType.LAZY),
      @JoinColumn(name = "acl_object_identity")
  }))
  private SecuredObject instance;


  @Setter
  @Getter(onMethod = @__({
      @Embedded
  }))
  private AuditResult auditResult;


}
