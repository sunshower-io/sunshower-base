package io.sunshower.model.api;

import io.sunshower.persistence.id.Identifier;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "PERMISSION")
public class Permission extends AbstractEntity<Identifier> implements GrantedAuthority {

  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = "name")
  }))
  private String name;


  @Setter
  @Getter(onMethod = @__({
      @ManyToOne(fetch = FetchType.LAZY),
      @JoinColumn(name = "grantee_id")
  }))
  private User grantee;

  @Setter
  @Getter(onMethod = @__({
      @OneToOne(cascade = CascadeType.ALL),
      @JoinColumn(name = "access_control_entry_id")
  }))
  private AccessControlEntry entry;

  @Override
  public String getAuthority() {
    return name;
  }

  public void setAuthority(String authority) {
    this.name = authority;
  }


}
