package io.inuka.ikola.annotations;

import org.springframework.security.access.prepost.PostAuthorize;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PostAuthorize("@objectLevelAuthorizer.isOwner(authentication,returnObject) || @objectLevelAuthorizer.isAuthorizedToView(authentication,returnObject)")
public @interface PostAuthorizeToView {
}