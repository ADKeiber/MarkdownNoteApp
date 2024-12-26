package com.adk.markdownnoteapp.service;

import com.adk.markdownnoteapp.dto.LanguageDTO;
import com.adk.markdownnoteapp.errorhandling.EntityNotFoundException;
import com.adk.markdownnoteapp.model.FileType;
import com.adk.markdownnoteapp.model.UserEntity;
import com.adk.markdownnoteapp.model.UserFile;
import com.adk.markdownnoteapp.repo.FileRepo;
import com.adk.markdownnoteapp.repo.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class FileService implements IFileService {

    private final Environment env;
    private final String languageToolURL;
    private final ObjectMapper objectMapper;
    private final FileRepo fileRepo;
    private final UserRepo userRepo;

    @Autowired
    FileService(Environment env, ObjectMapper objectMapper, FileRepo fileRepo, UserRepo userRepo){
        this.env = env;
        this.objectMapper = objectMapper;
        languageToolURL = env.getProperty("language-tool-api.url");
        this.fileRepo = fileRepo;
        this.userRepo = userRepo;
    }

    @Override
    public String uploadFile(MultipartFile file, String userId) {
        Optional<UserEntity> userOptional = userRepo.findById(userId);

        if(userOptional.isEmpty())
            throw new EntityNotFoundException(UserEntity.class, "id", userId);

        UserFile userFile = new UserFile();
        try {
            userFile.setMarkdownData(file.getBytes());
            userFile.setHtmlData(convertToHtml(file));
        } catch (Exception e) {
            //Throw some sort of error
        }

        userFile.setUser(userOptional.get());

        UserFile savedFile  = fileRepo.save(userFile);

        return savedFile.getId();
    }

    @Override
    public File getFileWithType(String fileId, FileType fileType) {
        Optional<UserFile> fileOptional = fileRepo.findById(fileId);

        if(fileOptional.isEmpty())
            throw new EntityNotFoundException(UserFile.class, "id", fileId);

        String fileName = switch(fileType) {
            case FileType.MARKDOWN -> fileOptional.get().getId() + ".md";
            case FileType.HTML -> fileOptional.get().getId() + ".html";
            default -> throw new IllegalStateException("Unexpected value: " + fileType);
        };


        File file = new File(fileName);
        try {
            if(fileType == FileType.MARKDOWN){
                FileUtils.writeByteArrayToFile(file, fileOptional.get().getMarkdownData());
            } else {
                FileUtils.writeByteArrayToFile(file, fileOptional.get().getHtmlData());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }

    @Override
    public String checkGrammar(FileType fileType, String fileId){

        return "";
    }

    @Override
    public String getAllFileIdsForUser(String userId){
        Optional<List<UserFile>> files = fileRepo.findByUserId(userId);

        if(files.isEmpty())
            throw new EntityNotFoundException(UserFile.class, "user.id", userId);
        return "";
    }

    @Override
    public String updateFile(MultipartFile file, String fileId) {
        Optional<UserFile> optionalUserFile = fileRepo.findById(fileId);

        if(optionalUserFile.isEmpty())
            throw new EntityNotFoundException(UserFile.class, "id", fileId);

        UserFile userFile = new UserFile();
        try {
            userFile.setMarkdownData(file.getBytes());
            userFile.setHtmlData(convertToHtml(file));
        } catch (Exception e) {
            //Throw some sort of error
        }

        userFile.setUser(optionalUserFile.get().getUser());

        UserFile savedFile  = fileRepo.save(userFile);

        return savedFile.getId();
    }

    public List<LanguageDTO> getSupportedLanguages() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(languageToolURL + "/v2/languages"))
                .method("GET", HttpRequest.BodyPublishers.noBody()).build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(),  objectMapper.getTypeFactory().constructCollectionType(List.class, LanguageDTO.class));
    }

    private byte[] convertToHtml(MultipartFile file) throws IOException {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(new String(file.getBytes()));
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document).getBytes();
    }

//    private
}
