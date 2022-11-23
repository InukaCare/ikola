package io.inuka.ikola;

import org.springframework.security.core.Authentication;

public interface ObjectLevelAuthorizer {
    boolean isAuthorizedToView(Object entity);

    boolean isAuthorizedToView(Authentication authentication, Object entity);

    boolean isAuthorizedToChange(Object entity);

    boolean isAuthorizedToChange(Authentication authentication, Object entity);

    boolean isOwner(Authentication authentication, Object entity);

    void assertIsAuthorizedToChange(Object entity);

    void assertIsAuthorizedToChange(Authentication authentication, Object entity);

    void assertIsAuthorizedToView(Object entity);

    void assertIsAuthorizedToView(Authentication authentication, Object entity);
}
