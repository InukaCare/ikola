package io.inuka.ikola.impl;

import io.inuka.ikola.ObjectAccessDecisionManager;
import io.inuka.ikola.ObjectAccessDecisionVoter;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public class DefaultObjectAccessDecisionManager implements ObjectAccessDecisionManager {
    protected List<ObjectAccessDecisionVoter> objectAccessDecisionVoters;

    public DefaultObjectAccessDecisionManager(List<ObjectAccessDecisionVoter> objectAccessDecisionVoters) {
        this.objectAccessDecisionVoters = objectAccessDecisionVoters;
    }

    @Override
    public boolean isAuthorizedToView(Authentication authentication, Object entity) {
        if (objectAccessDecisionVoters != null) {
            for (ObjectAccessDecisionVoter voter : objectAccessDecisionVoters) {
                Optional<Boolean> vote = voter.isAuthorizedToView(authentication, entity);
                if (vote.isPresent() && vote.get() == false) {
                    // un authorize if one of voters, voted negative
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isAuthorizedToChange(Authentication authentication, Object entity) {
        if (objectAccessDecisionVoters != null) {
            for (ObjectAccessDecisionVoter voter : objectAccessDecisionVoters) {
                Optional<Boolean> vote = voter.isAuthorizedToChange(authentication, entity);
                if (vote.isPresent() && vote.get() == false) {
                    // un authorize if one of voters, voted negative
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isAuthorizedToDelete(Authentication authentication, Object entity) {
        if (objectAccessDecisionVoters != null) {
            for (ObjectAccessDecisionVoter voter : objectAccessDecisionVoters) {
                Optional<Boolean> vote = voter.isAuthorizedToDelete(authentication, entity);
                if (vote.isPresent() && vote.get() == false) {
                    // un authorize if one of voters, voted negative
                    return false;
                }
            }
        }
        return true;
    }
}
