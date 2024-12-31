package com.adk.markdownnoteapp.service;

import com.adk.markdownnoteapp.dto.GrammarCheckDTO;
import com.adk.markdownnoteapp.dto.LanguageDTO;
import com.adk.markdownnoteapp.dto.MatchDTO;
import com.adk.markdownnoteapp.model.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Interface containing the required methods to interact with user files
 */
public interface IFileService {

    /**
     * Saves a file to a specific user
     * @param file {@link MultipartFile file} the file to upload
     * @param userId {@link String} the id of the user
     * @return {@link String} the id of newly saved file
     */
    String uploadFile(MultipartFile file, String userId);

    /**
     * Gets a file by its id in the specified file format
     * @param fileId {@link String} the id of the file
     * @param fileType {@link FileType} The type of file you wish to retrieve Ex: HTML, MARKDOWN
     * @return {@link File} the file of the specified type
     */
    File getFileWithType(String fileId, FileType fileType);

    /**
     * Gets the ids of the all the files associated with a user's ID
     * @param userId {@link String} the id of the user
     * @return List of {@link String} containing file IDs
     */
    List<String> getAllFileIdsForUser(String userId);

    /**
     * Updates a file with the given file ID.
     * @param file {@link MultipartFile} the new data of the file
     * @param fileId {@link String} the id of the file
     * @return {@link String} the id of the file
     */
    String updateFile(MultipartFile file, String fileId);

    /**
     * Gets a list of supported languages
     * @return List of {@link LanguageDTO} supported languages
     */
    List<LanguageDTO> getSupportedLanguages();

    /**
     * Checks the grammar of a file.
     * @param file {@link File} the file to check
     * @param language {@link String} the language code the file is written in
     * @return {@link GrammarCheckDTO} the data containing information about the grammar check
     */
    GrammarCheckDTO checkGrammar(File file, String language);
}
