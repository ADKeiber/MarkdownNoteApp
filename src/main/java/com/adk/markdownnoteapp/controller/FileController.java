package com.adk.markdownnoteapp.controller;

import com.adk.markdownnoteapp.model.FileType;
import com.adk.markdownnoteapp.repo.FileRepo;
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

import static com.adk.markdownnoteapp.model.FileType.MARKDOWN;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping(value = "/upload/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file, @PathVariable String userId){
        return new ResponseEntity<>(fileService.uploadFile(file, userId), HttpStatus.OK);
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
}
