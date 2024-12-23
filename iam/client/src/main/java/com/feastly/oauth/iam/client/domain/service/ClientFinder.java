package com.feastly.oauth.iam.client.domain.service;

import com.feastly.oauth.iam.client.domain.Client;
import com.feastly.oauth.iam.client.domain.repository.ClientRepository;
import jakarta.inject.Singleton;
import java.util.UUID;

@Singleton
public class ClientFinder {

    private final ClientRepository clientRepository;

    public ClientFinder(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client find(String id) {
        UUID uuid = parseClientId(id);

        return clientRepository.get(uuid)
            .orElseThrow(() -> new IllegalStateException(
                "No client found with the given ID: " + id));
    }

    private UUID parseClientId(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid client_id: Must be a valid UUID", e);
        }
    }

}
