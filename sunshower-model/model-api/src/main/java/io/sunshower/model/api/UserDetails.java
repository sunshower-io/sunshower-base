package io.sunshower.model.api;

import io.sunshower.arcus.condensation.Alias;
import io.sunshower.arcus.condensation.Attribute;
import io.sunshower.arcus.condensation.RootElement;
import io.sunshower.persistence.id.Identifier;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
