package io.sunshower.model.api;

import io.sunshower.arcus.condensation.Alias;
import io.sunshower.arcus.condensation.Attribute;
import io.sunshower.arcus.condensation.RootElement;
import io.sunshower.persistence.id.Identifier;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@RootElement
@Table(name = "USER_DETAILS")
public class UserDetails extends AbstractEntity<Identifier> implements IconAware {

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "first_name")}))
  @Attribute(alias = @Alias(read = "first-name", write = "first-name"))
  private String firstName;

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "last_name")}))
  @Attribute(alias = @Alias(read = "last-name", write = "last-name"))
  private String lastName;

  @Setter
  @Getter(onMethod = @__({@Embedded}))
  private Icon icon;

  @Setter
  @Getter(
      onMethod =
          @__({
            @MapsId,
            @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY),
            @JoinColumn(name = "id")
          }))
  private User user;

  public UserDetails() {}

  public UserDetails(@NonNull User user) {
    this.user = user;
    user.setDetails(this);
  }
}
