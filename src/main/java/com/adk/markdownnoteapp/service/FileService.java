package com.adk.markdownnoteapp.service;

import com.adk.markdownnoteapp.model.FileType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FileService implements IFileService {
    @Override
    public String uploadFile(MultipartFile file, String userId) {
        return "";
    }

    @Override
    public File getFileWithType(String fileId, FileType fileType) {
        return null;
    }
}
