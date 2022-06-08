package io.sunshower.model.api;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public class MediaType {

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "type")}))
  private String type;
}
