package com.feastly.oauth.shared.dto;

public class Response {
    private final boolean successful;
    private final String errorMessage;

    public Response(boolean successful, String errorMessage) {
        this.successful = successful;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
