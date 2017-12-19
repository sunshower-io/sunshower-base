package persist.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface TestService extends UserDetailsService {
    @PreAuthorize("isFullyAuthenticated()")
    void save();
}
