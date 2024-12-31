package com.adk.markdownnoteapp.repo;

import com.adk.markdownnoteapp.model.UserEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repo used to interact with users in a database
 */
@Repository
public interface UserRepo extends ListCrudRepository<UserEntity, String> {

    /**
     * Finds a user entity with the given username
     * @param username {@link String} the user name
     * @return {@link Optional} of {@link UserEntity} with the given username
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * If a user exists with the given username
     * @param username {@link String} the user's username
     * @return {@code true} if user with given username exists, {@code false} otherwise
     */
    Boolean existsByUsername(String username);
}
