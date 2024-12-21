package com.adk.markdownnoteapp.service;

import com.adk.markdownnoteapp.model.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface IFileService {
    /**
     * Important note: This file will be converted into 3 files
     * @param file
     * @param userId
     * @return
     */
    String uploadFile(MultipartFile file, String userId);

    File getFileWithType(String fileId, FileType fileType);
}
