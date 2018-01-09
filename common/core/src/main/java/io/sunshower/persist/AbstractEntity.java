package io.sunshower.persist;

import io.sunshower.common.Identifier;
import io.sunshower.persist.internal.jaxb.IdentifierAdapter;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

@MappedSuperclass
@XmlDiscriminatorNode("@type")
public class AbstractEntity extends SequenceIdentityAssignedEntity<Identifier> {

  static final transient Sequence<Identifier> DEFAULT_SEQUENCE;

  static {
    DEFAULT_SEQUENCE = Identifiers.newSequence(true);
  }

  @Id
  @XmlID
  @XmlJavaTypeAdapter(IdentifierAdapter.class)
  private Identifier id;

  protected AbstractEntity() {
    super(DEFAULT_SEQUENCE);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AbstractEntity)) return false;
    if (!super.equals(o)) return false;

    AbstractEntity that = (AbstractEntity) o;

    return id != null ? id.equals(that.id) : that.id == null;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (id != null ? id.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return String.format("%s{" + "id=" + id + '}', getClass());
  }
}
