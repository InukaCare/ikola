package io.inuka.ikola.impl;

import io.inuka.ikola.ObjectAccessDecisionVoter;
import io.inuka.ikola.ObjectAuthorizedRolesResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class RoleBasedAccessDecisionVoter implements ObjectAccessDecisionVoter {
    private final ObjectAuthorizedRolesResolver objectAuthorizedRolesResolver;

    public RoleBasedAccessDecisionVoter(ObjectAuthorizedRolesResolver objectAuthorizedRolesResolver) {
        this.objectAuthorizedRolesResolver = objectAuthorizedRolesResolver;
    }

    @Override
    public Optional<Boolean> isAuthorizedToView(Authentication authentication, Object entity) {
        Stream<String> userRoles = getAuthorizedRoles(authentication);
        Set<String> authorizedRoleToView = objectAuthorizedRolesResolver.resolveAuthorizedRoleToView(entity);
        return checkRequiredRoles(userRoles, authorizedRoleToView);
    }

    private Optional<Boolean> checkRequiredRoles(Stream<String> userRoles, Set<String> authorizedRoles) {
        boolean userHasOneOfRequiredRoles = userRoles.anyMatch(userRole -> authorizedRoles.contains(userRole));
        if (userHasOneOfRequiredRoles) {
            return Optional.of(true);
        } else {
            return Optional.of(false);
        }
    }

    @Override
    public Optional<Boolean> isAuthorizedToChange(Authentication authentication, Object entity) {
        Stream<String> userRoles = getAuthorizedRoles(authentication);
        Set<String> authorizedRoleToView = objectAuthorizedRolesResolver.resolveAuthorizedRoleToChange(entity);
        return checkRequiredRoles(userRoles, authorizedRoleToView);
    }

    @Override
    public Optional<Boolean> isAuthorizedToDelete(Authentication authentication, Object entity) {
        Stream<String> userRoles = getAuthorizedRoles(authentication);
        Set<String> authorizedRoleToView = objectAuthorizedRolesResolver.resolveAuthorizedRoleToDelete(entity);
        return checkRequiredRoles(userRoles, authorizedRoleToView);
    }

    private Stream<String> getAuthorizedRoles(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority);
    }
}
