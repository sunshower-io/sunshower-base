package io.sunshower.model.api;

import static io.sunshower.model.api.SecurityTables.ACL_OBJECT_IDENTITY;
import static io.sunshower.model.api.SecurityTables.AclObjectIdentityFields.INHERITS_ENTRIES;
import static io.sunshower.model.api.SecurityTables.AclObjectIdentityFields.OBJECT_IDENTITY_REFERENCE;
import static io.sunshower.model.api.SecurityTables.AclObjectIdentityFields.OBJECT_IDENTITY_TYPE;
import static io.sunshower.model.api.SecurityTables.AclObjectIdentityFields.PARENT_REFERENCE;

import io.sunshower.persistence.id.Identifier;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ACL_OBJECT_IDENTITY)
public class ObjectIdentity extends AbstractEntity<Identifier> {


  /**
   * determine whether this entry inherits its ancestor's permissions
   */
  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = INHERITS_ENTRIES)
  }))
  private boolean inherits;
  /**
   * referenced identity type
   * <p>
   * column name: object_id_identity
   */
  @Setter // you should not use this directly
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = OBJECT_IDENTITY_REFERENCE)
  }))
  private Identifier reference;

  /**
   * referenced secured object type column name: object_id_class
   */
  @Setter
  @Getter(
      onMethod =
      @__({
          @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL),
          @JoinColumn(name = OBJECT_IDENTITY_TYPE)
      }))
  private SecuredType object;

  /**
   * reference to parent column name: parent_object
   */
  @Setter
  @Getter(onMethod = @__({
      @ManyToOne,
      @JoinColumn(name = PARENT_REFERENCE)
  }))
  private ObjectIdentity parent;

  /**
   * mapped children
   */
  @Setter
  @Getter(onMethod = @__({
      @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
  }))
  private Set<ObjectIdentity> children;


}
