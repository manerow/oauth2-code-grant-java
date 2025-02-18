package com.feastly.oauth.iam.client.infrastructure.persistence;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import java.util.UUID;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface ClientCrudRepository extends CrudRepository<ClientEntity, UUID> {
}
