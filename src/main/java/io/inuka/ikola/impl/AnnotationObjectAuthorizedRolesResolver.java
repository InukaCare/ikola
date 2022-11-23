package io.inuka.ikola.impl;

import io.inuka.ikola.ObjectAuthorizedRolesResolver;

import java.util.Set;

public class AnnotationObjectAuthorizedRolesResolver implements ObjectAuthorizedRolesResolver {
    @Override
    public Set<String> resolveAuthorizedRoleToView(Object entity) {
        return null;
    }

    @Override
    public Set<String> resolveAuthorizedRoleToChange(Object entity) {
        return null;
    }

    @Override
    public Set<String> resolveAuthorizedRoleToDelete(Object entity) {
        return null;
    }
}
