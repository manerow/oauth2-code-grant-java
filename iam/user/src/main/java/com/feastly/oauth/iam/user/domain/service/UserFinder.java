package com.feastly.oauth.iam.user.domain.service;


import com.feastly.oauth.iam.user.domain.User;
import com.feastly.oauth.iam.user.domain.repository.UserRepository;
import jakarta.inject.Singleton;

@Singleton
public class UserFinder {

    private final UserRepository userRepository;

    public UserFinder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User find(String username) {

        return userRepository.getByUserName(username)
            .orElseThrow(() -> new IllegalStateException(
                "No user found with the given username: " + username));
    }
}
