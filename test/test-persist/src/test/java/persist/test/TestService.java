package persist.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by haswell on 3/29/17.
 */
public interface TestService extends UserDetailsService {
    @PreAuthorize("isFullyAuthenticated()")
    void save();
}
