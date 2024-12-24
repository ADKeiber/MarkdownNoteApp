package com.adk.markdownnoteapp.repo;

import com.adk.markdownnoteapp.model.UserFile;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepo extends ListCrudRepository<UserFile, String> {
    Optional<List<UserFile>> findByUserId(String userId);

}