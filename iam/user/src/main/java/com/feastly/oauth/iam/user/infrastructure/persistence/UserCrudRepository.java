package com.feastly.oauth.iam.user.infrastructure.persistence;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.UUID;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserCrudRepository extends CrudRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(@Nonnull @NotBlank String username);
}

