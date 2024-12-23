package com.feastly.oauth.authorization.application.dto;

import com.feastly.oauth.shared.dto.Response;

public class AuthorizationValidationResponse extends Response {

    private final String authorizationCode;

    private AuthorizationValidationResponse(boolean successful, String errorDescription, String authorizationCode) {
        super(successful, errorDescription);
        this.authorizationCode = authorizationCode;
    }

    public static AuthorizationValidationResponse success(String authorizationCode) {
        return new AuthorizationValidationResponse(true, null, authorizationCode);
    }

    public static AuthorizationValidationResponse error(String errorMessage) {
        return new AuthorizationValidationResponse(false, errorMessage, null);
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }
}
