package com.feastly.oauth.iam.user.domain;

import io.micronaut.serde.annotation.Serdeable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@Serdeable
public class User implements Principal {

    private final UUID id;
    private final String username;
    private final String passwordHash;
    private final String roles;
    private final Set<String> authorizedScopes;

    private User(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.passwordHash = builder.passwordHash;
        this.roles = builder.roles;
        this.authorizedScopes = builder.authorizedScopes;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRoles() {
        return roles;
    }

    public Set<String> getAuthorizedScopes() {
        return authorizedScopes;
    }

    @Override
    public String getName() {
        return getUsername();
    }

    public static class Builder implements Supplier<User> {

        private UUID id;
        private String username;
        private String passwordHash;
        private String roles;
        private Set<String> authorizedScopes;

        public Builder withId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPasswordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder withRoles(String roles) {
            this.roles = roles;
            return this;
        }

        public Builder withAuthorizedScopes(Set<String> scopes) {
            this.authorizedScopes = new HashSet<>(
                authorizedScopes == null ? new ArrayList<>() : authorizedScopes);
            return this;
        }

        @Override
        public User get() {
            return new User(this);
        }
    }
}
