package com.adk.markdownnoteapp.repo;

import com.adk.markdownnoteapp.model.UserFile;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repo used to interact with files
 */
@Repository
public interface FileRepo extends ListCrudRepository<UserFile, String> {

    /**
     * Finds a list of files that are associated with a given userID
     * @param userId {@link String} the user's ID
     * @return {@link Optional} {@link List} of {@link UserFile} with a given user ID
     */
    Optional<List<UserFile>> findByUserId(String userId);

}