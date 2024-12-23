package com.feastly.oauth.authorization.domain.repository;

import com.feastly.oauth.authorization.domain.vo.UserReadModel;
import java.util.Optional;

public interface UserReadModelRepository {
    Optional<UserReadModel> findById(String userId);
}
