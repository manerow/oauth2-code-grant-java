package com.feastly.oauth.authorization.domain.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientReadModel {
    private final String clientId;
    private final Set<String> grantTypes;
    private final Set<String> redirectUris;
    private final Set<String> scopes;

    public ClientReadModel(String clientId, List<String> grantTypes, List<String> redirectUris,
        List<String> scopes) {
        this.clientId = clientId;
        this.grantTypes = new HashSet<>(grantTypes == null ? new ArrayList<>() : grantTypes);
        this.redirectUris = new HashSet<>(redirectUris == null ? new ArrayList<>() : redirectUris);
        this.scopes = new HashSet<>(scopes == null ? new ArrayList<>() : scopes);
    }

    public boolean isValidRedirectUri(String redirectUri) {
        return redirectUris.contains(redirectUri);
    }

    public boolean supportsGrantType(String grantType) {
        return grantTypes.contains(grantType);
    }

    public boolean supportsScopes(Collection<String> requestedScopes) {
        return scopes.containsAll(requestedScopes);
    }
}