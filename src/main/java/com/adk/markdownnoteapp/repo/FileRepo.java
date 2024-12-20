package com.adk.markdownnoteapp.repo;

import com.adk.markdownnoteapp.model.UserFile;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo extends ListCrudRepository<UserFile, String> {}