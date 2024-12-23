package com.feastly.oauth.iam.client.domain;

import io.micronaut.serde.annotation.Serdeable;

import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@Serdeable
public class Client {

    private final UUID id;
    private final String secret;
    private final String redirectUri;
    private final Set<String> scopes; // Updated to use Scopes value object
    private final String authorizedGrantTypes;

    // Constructor
    public Client(Builder builder) {
        this.id = builder.id;
        this.secret = builder.secret;
        this.redirectUri = builder.redirectUri;
        this.scopes = builder.scopes; // Initialize Scopes
        this.authorizedGrantTypes = builder.authorizedGrantTypes;
    }

    // Getters
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

    public void validateRedirectUri(String providedUri) {
        if (this.redirectUri == null || redirectUri.isEmpty()) {
            throw new IllegalStateException("redirectUri is not pre-registered and should be provided");
        }
        if (!this.redirectUri.equals(providedUri)) {
            throw new IllegalArgumentException("redirect_uri is pre-registered and should match");
        }
    }

    public void validateAuthorizationGrantType(String grantType) {
        if (authorizedGrantTypes == null) {
            throw new IllegalStateException("authorizedGrantTypes are not pre-registered and should be provided.");
        }

        if (!authorizedGrantTypes.contains(grantType)) {
            throw new IllegalArgumentException(
                "Authorization Grant type " + grantType + " is not allowed for this client");
        }
    }

    // Builder Class
    public static class Builder implements Supplier<Client> {

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

        @Override
        public Client get() {
            return new Client(this);
        }
    }
}
