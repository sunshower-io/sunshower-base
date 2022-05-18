package io.sunshower.model.api;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public class AuditResult {

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "audit_success")}))
  boolean success;

  @Getter(onMethod = @__({@Basic, @Column(name = "audit_failure")}))
  boolean failure;
}
