package com.adk.markdownnoteapp.controller;

import com.adk.markdownnoteapp.model.FileType;
import com.adk.markdownnoteapp.repo.FileRepo;
import com.adk.markdownnoteapp.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.adk.markdownnoteapp.model.FileType.MARKDOWN;

@RestController("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping(value = "/upload/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file, @PathVariable String userId){
        return new ResponseEntity<>(fileService.uploadFile(file, userId), HttpStatus.OK);
    }

    @PostMapping(value = "/get/{fileId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> getFileOfType(@RequestParam(name = "fileType", defaultValue = "MARKDOWN" ) FileType fileType, @PathVariable String fileId){
        return new ResponseEntity<>(fileService.getFileWithType(fileId, fileType), HttpStatus.OK);
    }
}
