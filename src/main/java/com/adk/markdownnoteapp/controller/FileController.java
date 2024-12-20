package com.adk.markdownnoteapp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController("/file")
public class FileController {
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("markdownFile") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "No File provided";
        }
        return "File uploaded";
    }
}
