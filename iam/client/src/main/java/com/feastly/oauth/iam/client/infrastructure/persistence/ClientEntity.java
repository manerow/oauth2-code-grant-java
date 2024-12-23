package com.feastly.oauth.iam.client.infrastructure.persistence;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@MappedEntity
public class ClientEntity {

    @Id
    private UUID id;
    private String secret;
    private String redirectUri;
    private Set<String> scopes;
    private String authorizedGrantTypes;

    public ClientEntity(Builder builder) {
        this.id = builder.id;
        this.secret = builder.secret;
        this.redirectUri = builder.redirectUri;
        this.scopes = builder.scopes;
        this.authorizedGrantTypes = builder.authorizedGrantTypes;
    }

    public UUID getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public static class Builder implements Supplier<ClientEntity> {

        private UUID id;
        private String secret;
        private String redirectUri;
        private Set<String> scopes;
        private String authorizedGrantTypes;

        public Builder withId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder withClientSecret(String clientSecret) {
            this.secret = clientSecret;
            return this;
        }

        public Builder withRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public Builder withScopes(Set<String> scopes) {
            this.scopes = scopes;
            return this;
        }

        public Builder withAuthorizedGrantTypes(String authorizedGrantTypes) {
            this.authorizedGrantTypes = authorizedGrantTypes;
            return this;
        }

        public ClientEntity get() {
            return new ClientEntity(this);
        }
    }
}
