package io.inuka.ikola;

import org.springframework.security.core.Authentication;

public interface ObjectAccessDecisionManager {
    boolean isAuthorizedToView(Authentication authentication, Object entity);
    boolean isAuthorizedToChange(Authentication authentication, Object entity);
    boolean isAuthorizedToDelete(Authentication authentication, Object entity);
}
