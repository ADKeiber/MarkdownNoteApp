package com.adk.markdownnoteapp.service;

import com.adk.markdownnoteapp.model.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface IFileService {
    String uploadFile(MultipartFile file, String userId);
    File getFileWithType(String fileId, FileType fileType);
    String checkGrammar(FileType fileType, String fileId);
    String getAllFileIdsForUser(String userId);
    String updateFile(MultipartFile file, String fileId);
}
