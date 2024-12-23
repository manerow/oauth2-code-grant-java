package com.feastly.oauth.authorization.application;


import com.feastly.oauth.authorization.application.dto.AuthorizationValidationResponse;
import com.feastly.oauth.authorization.domain.service.ClientAuthorizationRequestValidator;
import com.feastly.oauth.authorization.domain.service.UserAuthorizationRequestValidator;
import jakarta.inject.Singleton;
import java.util.Set;
import java.util.UUID;

@Singleton
public class ValidateAuthorizationRequestUseCase {

    private final ClientAuthorizationRequestValidator clientValidator;
    private final UserAuthorizationRequestValidator userValidator;

    public ValidateAuthorizationRequestUseCase(ClientAuthorizationRequestValidator clientValidator,
        UserAuthorizationRequestValidator userValidator) {

        this.clientValidator = clientValidator;
        this.userValidator = userValidator;
    }

    public AuthorizationValidationResponse handle(
        String clientId,
        String username,
        String grant,
        String redirectUri,
        Set<String> requestedScopes
    ) {
        try {

            if (clientId.isEmpty() || grant.isEmpty() || redirectUri.isEmpty()) {
                return AuthorizationValidationResponse.error(
                        "Required parameters are missing: client_id, response_type, redirect_uri.");
            }

            // 1. Validate client.
            clientValidator.validate(clientId, grant, redirectUri, requestedScopes);

            // 2. Validate User
            userValidator.validate(username, requestedScopes);

            // 3. Generate authorization code (simulate code generation).
            String authorizationCode = generateAuthorizationCode(clientId, username, requestedScopes);

            // Return success response with the generated authorization code
            return AuthorizationValidationResponse.success(authorizationCode);

        } catch (IllegalArgumentException | IllegalStateException e) {
            return AuthorizationValidationResponse.error(e.getMessage());
        }
    }

    private String generateAuthorizationCode(
        String clientId,
        String username,
        Set<String> requestedScopes
    ) {
        // TODO
        return UUID.randomUUID().toString();
    }


}
