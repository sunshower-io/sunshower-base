package io.sunshower.model.api;

import static io.sunshower.model.api.SecurityTables.AclSecurityIdentity.PRINCIPAL;
import static io.sunshower.model.api.SecurityTables.AclSecurityIdentity.SID;
import static io.sunshower.model.api.SecurityTables.SECURITY_IDENTITY;

import io.sunshower.persistence.id.Identifier;
import javax.annotation.Nullable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@SuppressWarnings("PMD")
@Table(name = SECURITY_IDENTITY)
public class SecurityIdentity extends AbstractEntity<Identifier> {

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = PRINCIPAL)}))
  private boolean principal;
  /** reference user by username */
  @Getter(onMethod = @__({@OneToOne(fetch = FetchType.LAZY), @JoinColumn(name = SID)}))
  private User owner;

  /** username: used to map owner */
  @Setter // you should not use this
  @Getter(onMethod = @__({@Basic, @Column(name = SID, insertable = false, updatable = false)}))
  private String username;

  /** @param owner the owner of this SID */
  public void setOwner(@Nullable User owner) {
    if (owner == null) {
      this.owner = null;
      this.username = null;
    } else {
      this.owner = owner;
      this.username = owner.getUsername();
    }
  }
}
