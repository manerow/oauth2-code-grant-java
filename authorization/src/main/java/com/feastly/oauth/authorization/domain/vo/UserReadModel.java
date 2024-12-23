package com.feastly.oauth.authorization.domain.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserReadModel {
    private final String userId;
    private final Set<String> authorizedScopes;

    public UserReadModel(String userId, List<String> authorizedScopes) {
        this.userId = userId;
        this.authorizedScopes = new HashSet<>(authorizedScopes == null ? new ArrayList<>() : authorizedScopes);
    }

    public boolean isAuthorizedForScopes(Collection<String> requestedScopes) {
        return authorizedScopes.containsAll(requestedScopes);
    }
}