package io.sunshower.model.api;

import static io.sunshower.model.api.SecurityTables.ACL_OBJECT_IDENTITY;
import static io.sunshower.model.api.SecurityTables.AclObjectIdentityFields.INHERITS_ENTRIES;
import static io.sunshower.model.api.SecurityTables.AclObjectIdentityFields.OBJECT_IDENTITY_REFERENCE;
import static io.sunshower.model.api.SecurityTables.AclObjectIdentityFields.OBJECT_IDENTITY_TYPE;
import static io.sunshower.model.api.SecurityTables.AclObjectIdentityFields.PARENT_REFERENCE;

import io.sunshower.persistence.id.Identifier;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ACL_OBJECT_IDENTITY)
public class ObjectIdentity extends AbstractEntity<Identifier> {

  /** determine whether this entry inherits its ancestor's permissions */
  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = INHERITS_ENTRIES)}))
  private boolean inherits;
  /**
   * referenced identity type
   *
   * <p>column name: object_id_identity
   */
  @Setter // you should not use this directly
  @Getter(onMethod = @__({@Basic, @Column(name = OBJECT_IDENTITY_REFERENCE)}))
  private Identifier reference;

  /** referenced secured object type column name: object_id_class */
  @Setter
  @Getter(
      onMethod =
          @__({
            @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL),
            @JoinColumn(name = OBJECT_IDENTITY_TYPE)
          }))
  private SecuredObject object;

  /** reference to parent column name: parent_object */
  @Setter
  @Getter(onMethod = @__({@ManyToOne, @JoinColumn(name = PARENT_REFERENCE)}))
  private ObjectIdentity parent;

  /** mapped children */
  @Setter
  @Getter(onMethod = @__({@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)}))
  private Set<ObjectIdentity> children;
}
