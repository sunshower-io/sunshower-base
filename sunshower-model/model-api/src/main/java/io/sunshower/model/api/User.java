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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = USER)
public class User extends AbstractEntity<Identifier> {

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


}
