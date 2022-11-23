package io.inuka.ikola;

import java.util.Set;

public interface ObjectAuthorizedRolesResolver {
    Set<String> resolveAuthorizedRoleToView(Object entity);
    Set<String> resolveAuthorizedRoleToChange(Object entity);
    Set<String> resolveAuthorizedRoleToDelete(Object entity);
}
