package io.inuka.ikola;

import io.inuka.ikola.impl.DefaultObjectLevelAuthorizerBuilder;
import org.springframework.context.annotation.Bean;

public abstract class ObjectLevelSecurityConfigurer {
    private ObjectLevelAuthorizerBuilder objectLevelAuthorizerBuilder;
    @Bean(name = "objectLevelAuthorizer")
    public ObjectLevelAuthorizer init() {
        if (objectLevelAuthorizerBuilder == null) {
            this.objectLevelAuthorizerBuilder = new DefaultObjectLevelAuthorizerBuilder();
        }
        configure(this.objectLevelAuthorizerBuilder);
        return objectLevelAuthorizerBuilder.build();
    }
    protected abstract void configure(ObjectLevelAuthorizerBuilder objectLevelAuthorizerBuilder);

    public ObjectLevelAuthorizerBuilder getObjectLevelAuthorizerBuilder() {
        return objectLevelAuthorizerBuilder;
    }

    public void setObjectLevelAuthorizerBuilder(ObjectLevelAuthorizerBuilder objectLevelAuthorizerBuilder) {
        this.objectLevelAuthorizerBuilder = objectLevelAuthorizerBuilder;
    }
}
