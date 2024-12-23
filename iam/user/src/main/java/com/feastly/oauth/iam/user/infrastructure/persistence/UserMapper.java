package com.feastly.oauth.iam.user.infrastructure.persistence;


import com.feastly.oauth.iam.user.domain.User;

public final class UserMapper {

    private UserMapper() {
    }

    public static User toDomain(UserEntity entity) {
        return new User.Builder()
            .withId(entity.getId())
            .withUsername(entity.getUsername())
            .withPasswordHash(entity.getPasswordHash())
            .withRoles(entity.getRoles())
            .withAuthorizedScopes(entity.getAuthorizedScopes())
            .get();
    }

    public static UserEntity toEntity(User user) {
        return new UserEntity.Builder()
            .withId(user.getId())
            .withUsername(user.getUsername())
            .withPasswordHash(user.getPasswordHash())
            .withRoles(user.getRoles())
            .withAuthorizedScopes(user.getAuthorizedScopes())
            .get();
    }

}
