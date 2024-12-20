package com.adk.markdownnoteapp.repo;

import com.adk.markdownnoteapp.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);

}
