package io.sunshower.model.api;

import static io.sunshower.model.api.SecurityTables.SECURED_OBJECT_TYPE;

import io.sunshower.arcus.persist.jpa.TypeConverter;
import io.sunshower.persistence.id.Identifier;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** entity relating an ObjectIdentity to a type */
@Entity
@Table(name = SECURED_OBJECT_TYPE)
public class SecuredObject extends AbstractEntity<Identifier> {

  /** the type this related to */
  @Setter
  @Getter(onMethod = @__({@Column(name = "class"), @Convert(converter = TypeConverter.class)}))
  private Class<?> type;
}
