package io.sunshower.model.api;

import io.sunshower.persistence.id.Identifier;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name = "USER_DETAILS")
public class UserDetails extends AbstractEntity<Identifier> {


  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = "first_name")
  }))
  private String firstName;

  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = "last_name")
  }))
  private String lastName;

  @Setter
  @Getter(onMethod = @__({
      @MapsId,
      @OneToOne(
          cascade = CascadeType.ALL,
          optional = false,
          fetch = FetchType.LAZY
      ),
      @JoinColumn(name = "id")
  }))
  private User user;

  public UserDetails() {

  }

  public UserDetails(@NonNull User user) {
    this.user = user;
    user.setDetails(this);
  }

}
