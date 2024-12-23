package com.feastly.oauth.authorization.domain.service;

import com.feastly.oauth.authorization.domain.exception.InvalidAuthorizationRequestException;
import com.feastly.oauth.authorization.domain.repository.ClientReadModelRepository;
import com.feastly.oauth.authorization.domain.vo.ClientReadModel;
import jakarta.inject.Singleton;
import java.util.Set;

@Singleton
public class ClientAuthorizationRequestValidator {

    private final ClientReadModelRepository clientRepository;

    public ClientAuthorizationRequestValidator(ClientReadModelRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void validate(String clientId, String grant, String redirectUri, Set<String> requestedScopes) {
        ClientReadModel client = clientRepository.findById(clientId)
            .orElseThrow(() -> new InvalidAuthorizationRequestException("Invalid client_id."));

        if (!client.supportsGrantType(grant)) {
            throw new InvalidAuthorizationRequestException("Invalid response_type.");
        }

        if (!client.isValidRedirectUri(redirectUri)) {
            throw new InvalidAuthorizationRequestException("Invalid redirect_uri.");
        }

        if (!client.supportsScopes(requestedScopes)) {
            throw new InvalidAuthorizationRequestException("Invalid scope.");
        }
    }
}
