package io.sunshower.model.api;

import static io.sunshower.model.api.SecurityTables.USER;
import static io.sunshower.model.api.SecurityTables.User.PASSWORD;
import static io.sunshower.model.api.SecurityTables.User.USERNAME;

import io.sunshower.persistence.id.Identifier;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import java.util.Collection;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = USER)
public class User extends AbstractEntity<Identifier> implements
    org.springframework.security.core.userdetails.UserDetails {



  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = "salt")
  }))
  private byte[] salt;

  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = "initialization_vector")
  }))
  private byte[] initializationVector;

  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = "locked")
  }))
  private boolean locked;

  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = "expired")
  }))
  private boolean expired;

  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = "created"),
      @Temporal(TemporalType.TIMESTAMP)
  }))
  private Date created;


  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = "last_authenticated"),
      @Temporal(TemporalType.TIMESTAMP)
  }))
  private Date lastAuthenticated;
  /**
   * username for this user
   */
  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = USERNAME)
  }))
  private String username;

  /**
   * password--always a salted hash
   */
  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = PASSWORD)
  }))
  private String password;


  @Getter(onMethod = @__({
      @OneToOne(
          mappedBy = "user",
          cascade = CascadeType.ALL,
          orphanRemoval = true
      )}))
  private UserDetails details;


  public void setDetails(UserDetails details) {
    if (details != null) {
      this.details = details;
      details.setUser(this);
    } else {
      this.details = null;
    }
  }


  @Transient
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  @Transient
  public boolean isAccountNonExpired() {
    return !expired;
  }

  @Override
  @Transient
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  @Transient
  public boolean isCredentialsNonExpired() {
    return !expired;
  }

  @Override
  @Transient
  public boolean isEnabled() {
    return isAccountNonExpired() && isAccountNonLocked();
  }
}
