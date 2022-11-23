package io.inuka.ikola.impl;

import io.inuka.ikola.ObjectOwnerResolver;

import java.util.*;
import java.util.function.Function;

public class LambdaExpressionObjectOwnerResolver implements ObjectOwnerResolver {
    private Map<Class, Function<Object,Set<Object>>> objectOwnerIdResolvers;

    public LambdaExpressionObjectOwnerResolver() {
        objectOwnerIdResolvers = new HashMap<>();
    }

    public LambdaExpressionObjectOwnerResolver(Map<Class, Function<Object, Set<Object>>> objectOwnerIdResolvers) {
        this.objectOwnerIdResolvers = objectOwnerIdResolvers;
    }

    public LambdaExpressionObjectOwnerResolver setObjectOwnerIdResolver(Class objectType, Function<Object,Set<Object>> function) {
        objectOwnerIdResolvers.put(objectType, function);
        return this;
    }


    @Override
    public Set<Object> resolveOwnerIds(Object entity) {
        Function<Object, Set<Object>> objectOwnerIdsResolverFunction = objectOwnerIdResolvers.get(entity.getClass());
        if(objectOwnerIdsResolverFunction!=null) {
            Object objectOwners = objectOwnerIdsResolverFunction.apply(entity);
            if(objectOwners instanceof Set) {
                return (Set)objectOwners;
            } else if(objectOwners instanceof Collection) {
                return new HashSet<>((Collection<?>) objectOwners);
            } else {
                Set<Object> objectOwnersSet = new HashSet<>(1);
                objectOwnersSet.add(objectOwners);
                return objectOwnersSet;
            }
        }
        return new HashSet<>(0);
    }
}
