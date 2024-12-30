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
    String uploadFile(MultipartFile file, String userId);
    File getFileWithType(String fileId, FileType fileType);
    String getAllFileIdsForUser(String userId);
    String updateFile(MultipartFile file, String fileId);
    List<LanguageDTO> getSupportedLanguages();
    GrammarCheckDTO checkGrammar(File file, String language) throws FileNotFoundException;
}
