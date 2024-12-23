package com.feastly.oauth.iam.client.infrastructure.persistence;

import com.feastly.oauth.iam.client.domain.Client;
import com.feastly.oauth.iam.client.domain.repository.ClientRepository;
import jakarta.inject.Singleton;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class ClientRepositoryAdapter implements ClientRepository {

    private final ClientCrudRepository repository;

    public ClientRepositoryAdapter(ClientCrudRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Client> get(UUID id) {
        return repository.findById(id)
            .map(ClientMapper::toDomain);
    }

    @Override
    public Client save(Client client) {
        ClientEntity clientEntity = ClientMapper.toEntity(client);
        return ClientMapper.toDomain(repository.save(clientEntity));
    }
}
