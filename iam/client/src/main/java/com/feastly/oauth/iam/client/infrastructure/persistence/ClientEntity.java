package com.feastly.oauth.iam.client.infrastructure.persistence;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@MappedEntity
public class ClientEntity {

    @Id
    private final UUID id;
    private final String secret;
    private final String redirectUri;
    private final String scopes;
    private final String authorizedGrantTypes;

    @DateCreated
    private Instant createdAt;

    @DateUpdated
    private Instant updatedAt;

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
        return scopes == null || scopes.isEmpty()
            ? Set.of()
            : Arrays.stream(scopes.split(" "))
                .collect(Collectors.toSet());
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public static class Builder implements Supplier<ClientEntity> {

        private UUID id;
        private String secret;
        private String redirectUri;
        private String scopes;
        private String authorizedGrantTypes;

        private static String serializeScopes(Set<String> scopes) {
            return scopes == null || scopes.isEmpty()
                ? ""
                : String.join(" ", scopes);
        }

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
            this.scopes = serializeScopes(scopes);
            return this;
        }

        public Builder withAuthorizedGrantTypes(String authorizedGrantTypes) {
            this.authorizedGrantTypes = authorizedGrantTypes;
            return this;
        }

        @Override
        public ClientEntity get() {
            return new ClientEntity(this);
        }
    }
}
