
- Check if user is authenticated on @GET /authorization. If not, redirect to login Thymeleaf controller.
- Generate authorization code following RFC best practices / recommendations.
- iam bounded context event based CRUD.
- 
- Be able to authenticate using:
  - Thymeleaf, 
  - Micronaut Security (To populate Micronaut SecurityService) -> Must use session authentication (https://guides.micronaut.io/latest/micronaut-security-session-database-authentication-maven-java.html)
  - Form authentication.


### Setting up bounded contexts
- A bounded context must be created for handling authorization under Oauth RFC section 4.1

    
### Understanding the OAuth 2.0 Authorization Code Flow

The flow described in **RFC 6749** Section 4.1 follows these main steps:

1. **(A) Client Initiates Authorization Request**:
    - **Action**: The **client** redirects the **user-agent** (typically a browser) to the **authorization server's `/authorize` endpoint**.
    - The query parameters include `client_id`, `redirect_uri`, `scope`, and `state`.
    - **Result**: The user is redirected to the authorization server to authenticate and authorize the request.

2. **(B) Authorization Server Authenticates the Resource Owner (User)**:
    - **Action**: The **authorization server** will handle authentication.
        - If the user is **already authenticated**, the server may skip the login step and proceed to asking the user for consent to the requested scopes (if necessary).
        - If the user is **not authenticated** (i.e., they are not logged in), the server will **present the login form**.
    - **Result**: The user submits their login credentials (username, password, etc.).

   **Key Point**: The **login form** appears in **Step (B)**, **after** the user has been redirected to the authorization server by the client. The login form is part of the **authentication process** that occurs before the authorization server decides whether to show the consent screen and whether the user is allowed to grant access to the requested scopes.

3. **(C) Authorization Server Issues Authorization Code**:
    - **Action**: After successful authentication (and potentially consent), the **authorization server** generates an **authorization code** and redirects the **user-agent** back to the **client's `redirect_uri`**.
    - The authorization code is included in the URL query parameters:
      ```
      https://your-client.com/callback?code=<authorization_code>&state=<opaque_value>
      ```

4. **(D) The client exchanges the authorization code** for an access token by sending a POST request to the token endpoint.

5. **(E) The authorization server returns an access token** to the client.



### `@GET /authorize` Endpoint design proposal:
When `/authorize` is called:
1. **Validation Process**:
    - Validate request parameters against local projections of `Client` and `User` in the `Authorization` context.
    - If additional validation is needed (e.g., checking the `Client`'s status or advanced user validation), query the `IAM` context through an ACL service.

2. **Projection Update Process**:
    - The `IAM` context publishes events.
    - The `Authorization` context consumes these events and updates its read model asynchronously.
    - Events to subscribe for **Client Validation**:
        - `ClientRegistered`
        - `ClientUpdated` --> TODO: Can it be more granular?
        - `ClientDeleted`
    - Events to subscribe for **User Validation**:
      - `UserRegistered`
      - `UserScopeUpdated`
      - `UserDeleted`

---

### 1. **Understand the Domain Concepts**
Section 4.1 of the OAuth RFC covers the authorization code grant type, which involves:
1. **Resource Owner** authenticating and authorizing the client.
2. **Authorization Server** issuing an authorization code and exchanging it for an access token.
3. **Client** requesting and using the token.

### 2. **Define the Context and Bounded Contexts**
Use DDD to separate concerns into different bounded contexts:
- **Authorization Context**: Handles authorization code issuance and exchange.
- **Authentication Context**: Validates resource owners and their credentials.
- **Token Management Context**: Issues and manages tokens.

---

### 3. **Core Domain Modules**

#### a) **Authorization Context**
Handles the core behavior of the authorization process.
- **Entities**
    - `AuthorizationRequest`: Represents a request for an authorization code. It contains the `clientId`, `redirectUri`, `responseType`, `state`, etc.
    - `AuthorizationCode`: Represents an issued authorization code with a limited lifetime and associated metadata (client, resource owner, etc.).

- **Value Objects**
    - `RedirectUri`: A validated URI to which the authorization response is sent.
    - `ClientId`: Encapsulates a valid client identifier.
    - `State`: Optional, used to maintain state between the request and callback.

- **Aggregates**
    - `AuthorizationSession`: Root aggregate encapsulating the authorization request and code issuance logic.

- **Domain Services**
    - `AuthorizationService`: Handles the issuance of authorization codes and validation of the requests.

---

#### b) **Authentication Context**
Focuses on validating resource owner credentials.
- **Entities**
    - `ResourceOwner`: Represents the end-user interacting with the system.
    - `AuthenticationSession`: Tracks active authentication attempts.

- **Aggregates**
    - `ResourceOwnerAggregate`: Encapsulates resource owner information and authentication.

- **Domain Services**
    - `AuthenticationService`: Responsible for verifying credentials and establishing authenticated sessions.

---

#### c) **Token Management Context**
Handles the generation and management of tokens.
- **Entities**
    - `AccessToken`: Represents a time-bound token with associated scopes and metadata.
    - `RefreshToken`: Optional, represents a token for refreshing an access token.

- **Aggregates**
    - `Token`: Encapsulates access and refresh tokens and manages their lifecycle.

- **Domain Services**
    - `TokenService`: Issues tokens and validates their authenticity.
