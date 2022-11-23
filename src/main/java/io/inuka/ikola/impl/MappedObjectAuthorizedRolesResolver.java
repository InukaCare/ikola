package io.inuka.ikola.impl;

import io.inuka.ikola.ObjectAuthorizedRolesResolver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MappedObjectAuthorizedRolesResolver implements ObjectAuthorizedRolesResolver {
    private Map<Class, Set<String>> authorizedToViewRoles;
    private Map<Class, Set<String>> authorizedToChangeRoles;
    private Map<Class, Set<String>> authorizedToDeleteRoles;

    public MappedObjectAuthorizedRolesResolver() {
        authorizedToViewRoles = new HashMap<>();
        authorizedToChangeRoles = new HashMap<>();
        authorizedToDeleteRoles = new HashMap<>();
    }

    public MappedObjectAuthorizedRolesResolver(Map<Class, Set<String>> authorizedToViewRoles, Map<Class, Set<String>> authorizedToChangeRoles, Map<Class, Set<String>> authorizedToDeleteRoles) {
        this.authorizedToViewRoles = authorizedToViewRoles;
        this.authorizedToChangeRoles = authorizedToChangeRoles;
        this.authorizedToDeleteRoles = authorizedToDeleteRoles;
    }

    public MappedObjectAuthorizedRolesResolver setObjectAuthorizedRoles(Class objectType, Set<String> authorizedToViewRoles, Set<String> authorizedToChangeRoles, Set<String> authorizedToDeleteRoles) {
        this.authorizedToViewRoles.put(objectType, authorizedToViewRoles);
        this.authorizedToChangeRoles.put(objectType, authorizedToChangeRoles);
        this.authorizedToDeleteRoles.put(objectType, authorizedToDeleteRoles);
        return this;
    }

    @Override
    public Set<String> resolveAuthorizedRoleToView(Object entity) {
        Set<String> authorizedRoles = authorizedToViewRoles.get(entity.getClass());
        if (authorizedRoles != null) {
            return authorizedRoles;
        } else {
            return new HashSet<>(0);
        }
    }

    @Override
    public Set<String> resolveAuthorizedRoleToChange(Object entity) {
        Set<String> authorizedRoles = authorizedToChangeRoles.get(entity.getClass());
        if (authorizedRoles != null) {
            return authorizedRoles;
        } else {
            return new HashSet<>(0);
        }
    }

    @Override
    public Set<String> resolveAuthorizedRoleToDelete(Object entity) {
        Set<String> authorizedRoles = authorizedToDeleteRoles.get(entity.getClass());
        if (authorizedRoles != null) {
            return authorizedRoles;
        } else {
            return new HashSet<>(0);
        }
    }
}
