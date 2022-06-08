package io.sunshower.model.api;

import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;

public class Session {

  public User getUser() {
    val authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      return (User) authentication.getDetails();
    }
    return null;
  }
}
