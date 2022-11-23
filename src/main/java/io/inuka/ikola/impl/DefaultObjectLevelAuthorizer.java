package io.inuka.ikola.impl;

import io.inuka.ikola.AuthenticatedUserIdResolver;
import io.inuka.ikola.ObjectAccessDecisionManager;
import io.inuka.ikola.ObjectLevelAuthorizer;
import io.inuka.ikola.ObjectOwnerResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DefaultObjectLevelAuthorizer implements ObjectLevelAuthorizer {
    // private static final Logger log = LoggerFactory.getLogger(IkolaAuthorizer.class);

    private final ObjectOwnerResolver objectOwnerResolver;
    private final AuthenticatedUserIdResolver authenticatedUserIdResolver;

    private final ObjectAccessDecisionManager objectAccessDecisionManager;


    public DefaultObjectLevelAuthorizer(ObjectOwnerResolver objectOwnerResolver, AuthenticatedUserIdResolver authenticatedUserIdResolver, ObjectAccessDecisionManager objectAccessDecisionManager) {
        this.objectOwnerResolver = objectOwnerResolver;
        this.authenticatedUserIdResolver = authenticatedUserIdResolver;
        this.objectAccessDecisionManager = objectAccessDecisionManager;
    }


    @Override
    public boolean isAuthorizedToView(Object entity) {
        Authentication currentUserAuthentication = SecurityContextHolder.getContext().getAuthentication();
        return this.isAuthorizedToView(currentUserAuthentication, entity);
    }

    @Override
    public boolean isAuthorizedToView(Authentication authentication, Object entity) {
        if (entity == null) {
            return true;
        }
        if (entity instanceof Optional<?>) {
            Optional<?> optional = (Optional<?>) entity;
            if (optional.isPresent()) {
                return isAuthorizedToView(authentication, optional.get());
            } else {
                return false;
            }
        }
        if (entity instanceof ResponseEntity) {
            return isAuthorizedToView(authentication, ((ResponseEntity<?>) entity).getBody());
        }
        if (entity instanceof List) {
            for (Object o : (List) entity) {
                if (isAuthorizedToView(authentication, o) == false) {
                    return false;
                }
            }
            // isAuthorizedToView was true for all list items
            return true;
        }

        if (objectAccessDecisionManager.isAuthorizedToView(authentication, entity)) {
            return true;
        } else {
            // log.warn("User authorities {} does not have any of the required roles.", authorities);
            return false;
        }
    }

    @Override
    public boolean isAuthorizedToChange(Object entity) {
        Authentication currentUserAuthentication = SecurityContextHolder.getContext().getAuthentication();
        return this.isAuthorizedToChange(currentUserAuthentication, entity);
    }

    @Override
    public boolean isAuthorizedToChange(Authentication authentication, Object entity) {
        if (entity == null) {
            return true;
        }
        if (entity instanceof Optional) {
            Optional optional = (Optional) entity;
            if (optional.isPresent()) {
                return isAuthorizedToChange(authentication, optional.get());
            } else {
                return false;
            }
        }
        if (entity instanceof ResponseEntity) {
            return isAuthorizedToChange(authentication, ((ResponseEntity<?>) entity).getBody());
        }
        if (entity instanceof List) {
            for (Object o : (List) entity) {
                if (isAuthorizedToChange(authentication, o) == false) {
                    return false;
                }
            }
            // isAuthorizedToChange was true for all list items
            return true;
        }

        if (objectAccessDecisionManager.isAuthorizedToChange(authentication, entity)) {
            return true;
        } else {
            // log.warn("User authorities {} does not have any of the required roles.", authorities);
            return false;
        }
    }

    @Override
    public boolean isOwner(Authentication authentication, Object entity) {
        if (entity == null) {
            return false;
        }
        if (entity instanceof Optional) {
            Optional optional = (Optional) entity;
            if (optional.isPresent()) {
                return isOwner(authentication, optional.get());
            } else {
                return false;
            }
        }
        if (entity instanceof ResponseEntity) {
            return isOwner(authentication, ((ResponseEntity<?>) entity).getBody());
        }
        if (entity instanceof List) {
            for (Object o : (List) entity) {
                if (isOwner(authentication, o) == false) {
                    return false;
                }
            }
            // isOwner was true for all list items
            return true;
        }
        Set<Object> ownerIds = this.objectOwnerResolver.resolveOwnerIds(entity);
        Object userId = getUserId(authentication);
        //TODO log warning for null ownerIds
        if (ownerIds != null && ownerIds.contains(userId)) {
            return true;
        }
        // log.warn("UserId {} is not the owner.", userId);
        return false;
    }

    @Override
    public void assertIsAuthorizedToChange(Object entity) {
        Authentication currentUserAuthentication = SecurityContextHolder.getContext().getAuthentication();
        assertIsAuthorizedToChange(currentUserAuthentication, entity);
    }

    @Override
    public void assertIsAuthorizedToChange(Authentication authentication, Object entity) {
        if (!isOwner(authentication, entity) && !isAuthorizedToChange(authentication, entity)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public void assertIsAuthorizedToView(Object entity) {
        Authentication currentUserAuthentication = SecurityContextHolder.getContext().getAuthentication();
        assertIsAuthorizedToView(currentUserAuthentication, entity);
    }

    @Override
    public void assertIsAuthorizedToView(Authentication authentication, Object entity) {
        if (!isOwner(authentication, entity) && !isAuthorizedToView(authentication, entity)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    private Object getUserId(Authentication authentication) {
        return authenticatedUserIdResolver.resolveUserId(authentication);
    }

}
