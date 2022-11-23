package io.inuka.ikola;

import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface ObjectAccessDecisionVoter {
    Optional<Boolean> isAuthorizedToView(Authentication authentication, Object entity);
    Optional<Boolean> isAuthorizedToChange(Authentication authentication, Object entity);
    Optional<Boolean> isAuthorizedToDelete(Authentication authentication, Object entity);
}
