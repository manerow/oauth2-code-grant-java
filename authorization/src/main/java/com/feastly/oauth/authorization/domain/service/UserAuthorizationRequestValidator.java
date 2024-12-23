package com.feastly.oauth.authorization.domain.service;

import com.feastly.oauth.authorization.domain.exception.InvalidAuthorizationRequestException;
import com.feastly.oauth.authorization.domain.repository.UserReadModelRepository;
import com.feastly.oauth.authorization.domain.vo.UserReadModel;
import jakarta.inject.Singleton;
import java.util.Set;

@Singleton
public class UserAuthorizationRequestValidator {

    private final UserReadModelRepository userRepository;

    public UserAuthorizationRequestValidator(UserReadModelRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validate(String userId, Set<String> requestedScopes) {
        UserReadModel user = userRepository.findById(userId)
            .orElseThrow(() -> new InvalidAuthorizationRequestException("Invalid user_id."));

        if (!user.isAuthorizedForScopes(requestedScopes)) {
            throw new InvalidAuthorizationRequestException("User does not have the required scopes.");
        }
    }
}

