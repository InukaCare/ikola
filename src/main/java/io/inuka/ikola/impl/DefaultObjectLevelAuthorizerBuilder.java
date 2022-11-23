package io.inuka.ikola.impl;

import io.inuka.ikola.*;

import java.util.ArrayList;
import java.util.List;

public class DefaultObjectLevelAuthorizerBuilder implements ObjectLevelAuthorizerBuilder {
    private ObjectOwnerResolver objectOwnerResolver;
    private AuthenticatedUserIdResolver authenticatedUserIdResolver;
    private List<ObjectAccessDecisionVoter> objectAccessDecisionVoters = new ArrayList<>();

    @Override
    public ObjectLevelAuthorizerBuilder withObjectOwnerResolver(ObjectOwnerResolver objectOwnerResolver) {
        this.objectOwnerResolver = objectOwnerResolver;
        return this;
    }

    @Override
    public ObjectLevelAuthorizerBuilder withAuthenticatedUserIdResolver(AuthenticatedUserIdResolver authenticatedUserIdResolver) {
        this.authenticatedUserIdResolver = authenticatedUserIdResolver;
        return this;
    }

    @Override
    public ObjectLevelAuthorizerBuilder withRoleBasedAccess(ObjectAuthorizedRolesResolver objectAuthorizedRolesResolver) {
        RoleBasedAccessDecisionVoter roleBasedAccessDecisionVoter = new RoleBasedAccessDecisionVoter(objectAuthorizedRolesResolver);
        this.objectAccessDecisionVoters.add(roleBasedAccessDecisionVoter);
        return this;
    }

    @Override
    public ObjectLevelAuthorizer build() {
        DefaultObjectAccessDecisionManager decisionManager = new DefaultObjectAccessDecisionManager(this.objectAccessDecisionVoters);
        ObjectLevelAuthorizer objectLevelAuthorizer = new DefaultObjectLevelAuthorizer(this.objectOwnerResolver, this.authenticatedUserIdResolver, decisionManager);
        return objectLevelAuthorizer;
    }
}
