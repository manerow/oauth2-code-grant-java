package com.feastly.oauth.iam.user.domain.repository;

import com.feastly.oauth.iam.user.domain.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> get(UUID id);

    Optional<User> getByUserName(String username);

    User save(User user);

}
