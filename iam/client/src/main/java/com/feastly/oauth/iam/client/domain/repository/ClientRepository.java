package com.feastly.oauth.iam.client.domain.repository;

import com.feastly.oauth.iam.client.domain.Client;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {

    Optional<Client> get(UUID id);

    Client save(Client user);
}
