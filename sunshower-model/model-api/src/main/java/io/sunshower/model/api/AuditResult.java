package io.sunshower.model.api;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public class AuditResult {

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "audit_success")}))
  boolean success;

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "audit_failure")}))
  boolean failure;
}
