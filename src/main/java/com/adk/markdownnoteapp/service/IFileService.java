package com.adk.markdownnoteapp.service;

import com.adk.markdownnoteapp.dto.LanguageDTO;
import com.adk.markdownnoteapp.dto.MatchDTO;
import com.adk.markdownnoteapp.model.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface IFileService {
    String uploadFile(MultipartFile file, String userId);
    File getFileWithType(String fileId, FileType fileType);
    String getAllFileIdsForUser(String userId);
    String updateFile(MultipartFile file, String fileId);
    List<LanguageDTO> getSupportedLanguages();
    List<MatchDTO> checkGrammar(File file, String language);
}
