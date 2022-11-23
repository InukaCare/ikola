package io.inuka.ikola;

public interface ObjectLevelAuthorizerBuilder {
    ObjectLevelAuthorizerBuilder withObjectOwnerResolver(ObjectOwnerResolver objectOwnerResolver);
    ObjectLevelAuthorizerBuilder withAuthenticatedUserIdResolver(AuthenticatedUserIdResolver authenticatedUserIdResolver);
    ObjectLevelAuthorizerBuilder withRoleBasedAccess(ObjectAuthorizedRolesResolver objectAuthorizedRolesResolver);
    ObjectLevelAuthorizer build();
}
