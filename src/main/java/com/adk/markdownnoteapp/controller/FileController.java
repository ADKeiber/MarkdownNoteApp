package com.adk.markdownnoteapp.controller;

import com.adk.markdownnoteapp.errorhandling.ApiError;
import com.adk.markdownnoteapp.model.FileType;
import com.adk.markdownnoteapp.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.adk.markdownnoteapp.dto.GrammarCheckDTO;
import com.adk.markdownnoteapp.dto.LanguageDTO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Controller used to interact with files
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    /**
     * Saves a file to a given user
     * @param file {@link MultipartFile} the file to save
     * @param userId {@link String} the user's ID
     * @return {@link ResponseEntity} containing the fileID if no exceptions thrown, {@link ApiError} otherwise
     */
    @PostMapping(value = "/upload/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file, @PathVariable String userId){
        return new ResponseEntity<>(fileService.uploadFile(file, userId), HttpStatus.OK);
    }

    /**
     * Updates a file
     * @param file {@link MultipartFile} the new data stored in the file
     * @param fileId {@link String} the id of the file to update
     * @return {@link ResponseEntity} containing the fileID if no exceptions thrown, {@link ApiError} otherwise
     */
    @PostMapping(value = "/update/{fileId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateFile(@RequestParam MultipartFile file, @PathVariable String fileId){
        return new ResponseEntity<>(fileService.updateFile(file, fileId), HttpStatus.OK);
    }

    /**
     * Retrieves a file of the given type
     * @param fileType {@link FileType} the type of file to return
     * @param fileId {@link String} the id of the file to return
     * @return {@link ResponseEntity} containing the file if no exceptions thrown, {@link ApiError} otherwise
     */
    @GetMapping(value = "/get/{fileId}") // continue https://www.youtube.com/watch?v=wW0nVc2NlhA to improve method
    public ResponseEntity<?> getFileOfType(@RequestParam(name = "fileType", defaultValue = "MARKDOWN" ) FileType fileType, @PathVariable String fileId) {
        File file = fileService.getFileWithType(fileId, fileType);
        try {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\"" + file.getName() + "\"")
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(Files.newInputStream(file.toPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks the grammar of a file
     * @param fileId {@link String} the id of the file that's grammar will be checked
     * @param language {@link String} the language code of the text
     * @return {@link ResponseEntity} containing the {@link GrammarCheckDTO} if no exceptions thrown, {@link ApiError} otherwise
     */
    @GetMapping(value = "/checkGrammar/{fileId}")
    public ResponseEntity<?> checkGrammar(@PathVariable String fileId, @RequestParam String language) {
        File file = fileService.getFileWithType(fileId, FileType.HTML);
        return new ResponseEntity<>(fileService.checkGrammar(file, language), HttpStatus.OK);
    }

    /**
     * Gets the supported languages for the grammar check
     * @return {@link ResponseEntity} containing the {@link LanguageDTO}s if no exceptions thrown, {@link ApiError} otherwise
     */
    @GetMapping("/grammar/supportedLanguages")
    public ResponseEntity<Object> getSupportedLanguages() {
        return new ResponseEntity<>(fileService.getSupportedLanguages(), HttpStatus.OK);
    }

    /**
     * Gets the IDs of all files associated with a user
     * @param userId {@link String} the id of the user
     * @return {@link ResponseEntity} containing the fileIds if no exceptions thrown, {@link ApiError} otherwise
     */
    @GetMapping(value = "/getFileIds/{userId}")
    public ResponseEntity<?> getAllFileIdsForUser(@PathVariable String userId){
        return new ResponseEntity<>(fileService.getAllFileIdsForUser(userId), HttpStatus.OK);
    }

}
