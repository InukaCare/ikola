package io.inuka.ikola;

import java.util.Set;

public interface ObjectOwnerResolver {
    Set<Object> resolveOwnerIds(Object entity);

}
