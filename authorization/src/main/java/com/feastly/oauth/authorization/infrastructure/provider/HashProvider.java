package com.feastly.oauth.authorization.infrastructure.provider;

public interface HashProvider {

    String hashPassword(String plainTextPassword);

    boolean validatePassword(String plainTextPassword, String storedHash);

    boolean needsRehash(String storedHash);

}
