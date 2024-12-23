package com.feastly.oauth.iam.client.infrastructure.persistence;

import com.feastly.oauth.iam.client.domain.Client;

public final class ClientMapper {

    private ClientMapper() {
    }

    public static Client toDomain(ClientEntity entity) {
        return new Client.Builder()
            .withId(entity.getId())
            .withClientSecret(entity.getSecret())
            .withRedirectUri(entity.getRedirectUri())
            .withScopes(entity.getScopes())
            .withAuthorizedGrantTypes(entity.getAuthorizedGrantTypes())
            .get();
    }


    public static ClientEntity toEntity(Client client) {
        return new ClientEntity.Builder()
            .withId(client.getId())
            .withClientSecret(client.getSecret())
            .withRedirectUri(client.getRedirectUri())
            .withScopes(client.getScopes())
            .withAuthorizedGrantTypes(client.getAuthorizedGrantTypes())
            .get();
    }
}
