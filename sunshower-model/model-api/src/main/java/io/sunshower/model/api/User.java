package io.sunshower.model.api;

import static io.sunshower.model.api.SecurityTables.USER;
import static io.sunshower.model.api.SecurityTables.User.PASSWORD;
import static io.sunshower.model.api.SecurityTables.User.USERNAME;

import io.sunshower.arcus.condensation.Alias;
import io.sunshower.arcus.condensation.Attribute;
import io.sunshower.arcus.condensation.Convert;
import io.sunshower.arcus.condensation.Element;
import io.sunshower.arcus.condensation.RootElement;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.security.core.GrantedAuthority;

@Entity
@RootElement
@Table(name = USER)
@SuppressWarnings("PMD")
public class User extends TenantedEntity
    implements org.springframework.security.core.userdetails.UserDetails {

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "salt")}))
  @Attribute
  @Convert(Base58Converter.class)
  private byte[] salt;

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "initialization_vector")}))
  @Attribute(alias = @Alias(read = "initialization-vector", write = "initialization-vector"))
  @Convert(Base58Converter.class)
  private byte[] initializationVector;

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "locked")}))
  @Attribute
  private boolean locked;

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "expired")}))
  @Attribute
  private boolean expired;

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "created"), @Temporal(TemporalType.TIMESTAMP)}))
  @Attribute
  @Convert(DateConverter.class)
  private Date created;

  @Setter
  @Getter(
      onMethod =
          @__({@Basic, @Column(name = "last_authenticated"), @Temporal(TemporalType.TIMESTAMP)}))
  @Attribute(alias = @Alias(read = "last-authenticated", write = "last-authenticated"))
  @Convert(DateConverter.class)
  private Date lastAuthenticated;
  /** username for this user */
  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = USERNAME)}))
  @Attribute
  private String username;

  /** password--always a salted hash */
  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = PASSWORD)}))
  @Attribute
  private String password;

  /** a role is a category of user that grants or prohibits access to system-functionality */
  @Setter
  @Getter(onMethod = @__({@ManyToMany(mappedBy = "users")}))
  private Set<Role> roles;

  @Setter
  @Getter(onMethod = @__({@ManyToMany(mappedBy = "users")}))
  private Set<Group> groups;

  /** a permission is a granted authority that grants a specific user access to a specific object */
  @Setter
  @Getter(
      onMethod =
          @__({
            @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true),
          }))
  private Set<Permission> permissions;

  @Getter(
      onMethod =
          @__({@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)}))
  @Element
  private UserDetails details;

  public User() {}

  public User(org.springframework.security.core.userdetails.UserDetails user) {
    setUsername(user.getUsername());
    setPassword(user.getPassword());
    val details = new UserDetails();
    details.setUser(this);
    setDetails(details);
  }

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
