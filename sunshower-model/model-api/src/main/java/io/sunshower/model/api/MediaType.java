package io.sunshower.model.api;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public class MediaType {

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "type")}))
  private String type;
}
