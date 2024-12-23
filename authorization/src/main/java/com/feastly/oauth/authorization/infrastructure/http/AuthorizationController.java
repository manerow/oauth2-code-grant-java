package com.feastly.oauth.authorization.infrastructure.http;


import com.feastly.oauth.authorization.application.ValidateAuthorizationRequestUseCase;
import com.feastly.oauth.authorization.application.dto.AuthorizationValidationResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;
import java.net.URI;
import java.util.Set;

@Controller("/authorize")
public class AuthorizationController {

    private final ValidateAuthorizationRequestUseCase validateAuthorizationRq;
    private final SecurityService securityService;
    private static final String LOGIN_PAGE_URI = "/login";

    @Inject
    public AuthorizationController(
        ValidateAuthorizationRequestUseCase validateAuthorizationRq,
        SecurityService securityService
    ) {
        this.validateAuthorizationRq = validateAuthorizationRq;
        this.securityService = securityService;
    }

    @Get
    public HttpResponse<String> authorize(
        @QueryValue("client_id") String clientId,
        @QueryValue("response_type") String responseType,
        @QueryValue(value = "redirect_uri", defaultValue = "") String redirectUri,
        @QueryValue(value = "scope", defaultValue = "") String requestedScope,
        @QueryValue(value = "state", defaultValue = "") String state
    ) {

        if (!securityService.isAuthenticated()) {
            // If not authenticated, redirect to the login page
            return HttpResponse.seeOther(URI.create(LOGIN_PAGE_URI)); // TODO: Can this be archived with Thymeleaf?
        }

        AuthorizationValidationResponse validationResponse = validateAuthorizationRq.handle(
            clientId,
            securityService.username().orElse(null),
            responseType,
            redirectUri,
            Set.of(requestedScope.split(" "))
        );


        if (!validationResponse.isSuccessful()) {
            // Render error page or redirect with an error if validation fails
            String errorRedirectUri = String.format("%s?error=%s", redirectUri, validationResponse.getErrorMessage());
            return HttpResponse.seeOther(URI.create(errorRedirectUri));
        }

        // If successful, generate the authorization code and redirect
        String authorizationCode = validationResponse.getAuthorizationCode();
        String successRedirectUri = String.format("%s?code=%s&state=%s", redirectUri, authorizationCode, state);
        return HttpResponse.seeOther(URI.create(successRedirectUri));
    }

}
