package com.feastly.oauth.iam.user.infrastructure.persistence;

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
public class UserEntity {

    @Id
    private UUID id;
    private String username;
    private String passwordHash;
    private String roles;
    private String scopes;

    @DateCreated
    private Instant createdAt;

    @DateUpdated
    private Instant updatedAt;

    private UserEntity(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.passwordHash = builder.passwordHash;
        this.roles = builder.roles;
        this.scopes = builder.scopes;
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

    public Set<String> getScopes() {
        return scopes == null || scopes.isEmpty()
            ? Set.of()
            : Arrays.stream(scopes.split(" "))
                .collect(Collectors.toSet());
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public static class Builder implements Supplier<UserEntity> {

        private UUID id;
        private String username;
        private String passwordHash;
        private String roles;
        private String scopes;

        private static String serializeScopes(Set<String> scopes) {
            return scopes == null || scopes.isEmpty()
                ? ""
                : String.join(" ", scopes);
        }

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

        public Builder withScopes(Set<String> scopes) {
            this.scopes = serializeScopes(scopes);
            return this;
        }

        @Override
        public UserEntity get() {
            return new UserEntity(this);
        }
    }
}
