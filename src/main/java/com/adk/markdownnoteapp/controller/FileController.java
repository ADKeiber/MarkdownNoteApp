package com.adk.markdownnoteapp.controller;

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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping(value = "/upload/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file, @PathVariable String userId){
        return new ResponseEntity<>(fileService.uploadFile(file, userId), HttpStatus.OK);
    }

    @PostMapping(value = "/update/{fileId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateFile(@RequestParam MultipartFile file, @PathVariable String fileId){
        return new ResponseEntity<>(fileService.updateFile(file, fileId), HttpStatus.OK);
    }

    @GetMapping(value = "/get/{fileId}") // continue https://www.youtube.com/watch?v=wW0nVc2NlhA to improve method
    public ResponseEntity<?> getFileOfType(@RequestParam(name = "fileType", defaultValue = "MARKDOWN" ) FileType fileType, @PathVariable String fileId) throws IOException {
        File file = fileService.getFileWithType(fileId, fileType);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\"" + file.getName() + "\"")
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(Files.newInputStream(file.toPath())));

    }

    @GetMapping(value = "/checkGrammar/{fileId}")
    public ResponseEntity<?> checkGrammar(@RequestParam(name = "fileType", defaultValue = "MARKDOWN" ) FileType fileType, @PathVariable String fileId){
        return new ResponseEntity<>(fileService.checkGrammar(fileType, fileId), HttpStatus.OK);
    }

    @GetMapping("/grammar/supportedLanguages")
    public ResponseEntity<Object> getSupportedLanguages() throws IOException, InterruptedException {

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping(value = "/getFileIds/{userId}")
    public ResponseEntity<?> getAllFileIdsForUser(@PathVariable String userId){
        return new ResponseEntity<>(fileService.getAllFileIdsForUser(userId), HttpStatus.OK);
    }

}
