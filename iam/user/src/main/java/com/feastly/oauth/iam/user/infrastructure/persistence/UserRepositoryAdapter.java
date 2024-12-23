package com.feastly.oauth.iam.user.infrastructure.persistence;


import com.feastly.oauth.iam.user.domain.User;
import com.feastly.oauth.iam.user.domain.repository.UserRepository;
import jakarta.inject.Singleton;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class UserRepositoryAdapter implements UserRepository {

    private final UserCrudRepository repository;

    public UserRepositoryAdapter(UserCrudRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> get(UUID id) {
        Optional<UserEntity> userEntity = repository.findById(id);
        return userEntity.map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> getByUserName(String username) {
        Optional<UserEntity> userEntity = repository.findByUsername(username);
        return userEntity.map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        return UserMapper.toDomain(repository.save(userEntity));
    }
}
