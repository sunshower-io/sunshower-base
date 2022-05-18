package io.sunshower.model.api;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public class Icon {

  @Setter
  @Getter(onMethod = @__({
      @Embedded,
      @Column(name = "media_type")
  }))
  private MediaType mediaType;


  @Setter
  @Getter(onMethod = @__({
      @Lob,
      @Column(length = 1000000)
  }))
  private byte[] data;


}
