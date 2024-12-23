package com.feastly.oauth.authorization.domain.repository;

import com.feastly.oauth.authorization.domain.vo.ClientReadModel;
import java.util.Optional;

public interface ClientReadModelRepository {
    Optional<ClientReadModel> findById(String id);

}
