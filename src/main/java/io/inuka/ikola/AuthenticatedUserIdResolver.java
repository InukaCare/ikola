package io.inuka.ikola;

import org.springframework.security.core.Authentication;


public interface AuthenticatedUserIdResolver {
    Object resolveUserId(Authentication authentication);

}
