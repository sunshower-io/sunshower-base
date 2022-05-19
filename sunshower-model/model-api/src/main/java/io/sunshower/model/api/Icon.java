package io.sunshower.model.api;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public class Icon {

  @Setter
  @Getter(onMethod = @__({@Embedded}))
  private MediaType mediaType;

  @Setter
  @Getter(onMethod = @__({@Lob, @Column(length = 1000000, name = "icon")}))
  private byte[] data;
}
