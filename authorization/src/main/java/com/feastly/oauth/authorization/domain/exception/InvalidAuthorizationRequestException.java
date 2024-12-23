package com.feastly.oauth.authorization.domain.exception;

public class InvalidAuthorizationRequestException extends RuntimeException {
    private final String errorCode;
    private final String description;

    /**
     * Constructs a new InvalidAuthorizationRequestException with a specific error code and description.
     *
     * @param errorCode    The error code representing the specific type of authorization failure.
     * @param description  A detailed message describing the cause of the error.
     */
    public InvalidAuthorizationRequestException(String errorCode, String description) {
        super(String.format("%s: %s", errorCode, description));
        this.errorCode = errorCode;
        this.description = description;
    }

    /**
     * Constructs a new InvalidAuthorizationRequestException with a description only.
     *
     * @param description A detailed message describing the cause of the error.
     */
    public InvalidAuthorizationRequestException(String description) {
        super(description);
        this.errorCode = "invalid_request"; // Default OAuth 2.0 error code
        this.description = description;
    }

    /**
     * Returns the error code associated with this exception.
     *
     * @return the error code.
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Returns the detailed message associated with this exception.
     *
     * @return the detailed error description.
     */
    public String getDescription() {
        return description;
    }
}

