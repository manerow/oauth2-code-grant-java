package com.feastly.oauth.iam.user.infrastructure.persistence;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@MappedEntity
public class UserEntity {

    @Id
    private UUID id;
    private String username;
    private String passwordHash;
    private String roles;
    private Set<String> authorizedScopes;

    // Private constructor to prevent direct instantiation
    private UserEntity(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.passwordHash = builder.passwordHash;
        this.roles = builder.roles;
        this.authorizedScopes = builder.authorizedScopes;
    }

    // Getters
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

    // Builder Class for UserEntity
    public static class Builder implements Supplier<UserEntity> {

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
            this.authorizedScopes = scopes;
            return this;
        }

        @Override
        public UserEntity get() {
            return new UserEntity(this);
        }
    }
}
