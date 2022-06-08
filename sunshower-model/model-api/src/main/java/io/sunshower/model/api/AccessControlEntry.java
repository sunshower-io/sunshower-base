package io.sunshower.model.api;

import static io.sunshower.model.api.SecurityTables.ACCESS_CONTROL_ENTRY;

import io.sunshower.persistence.id.Identifier;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ACCESS_CONTROL_ENTRY)
public class AccessControlEntry extends AbstractEntity<Identifier> {

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "ace_order")}))
  private int order;

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "mask")}))
  private int mask;

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "granting")}))
  private boolean granting;

  @Setter
  @Getter(onMethod = @__({@OneToOne, @JoinColumn(name = "sid")}))
  private SecurityIdentity identity;

  @Setter
  @Getter(
      onMethod =
          @__({@OneToOne(fetch = FetchType.LAZY), @JoinColumn(name = "acl_object_identity")}))
  private SecuredObject instance;

  @Setter
  @Getter(onMethod = @__({@Embedded}))
  private AuditResult auditResult;
}
