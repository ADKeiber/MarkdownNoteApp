package com.adk.markdownnoteapp.service;

import com.adk.markdownnoteapp.dto.GrammarCheckDTO;
import com.adk.markdownnoteapp.dto.LanguageDTO;
import com.adk.markdownnoteapp.dto.MatchDTO;
import com.adk.markdownnoteapp.errorhandling.EntityNotFoundException;
import com.adk.markdownnoteapp.errorhandling.ThirdPartyAPIException;
import com.adk.markdownnoteapp.model.FileType;
import com.adk.markdownnoteapp.model.UserEntity;
import com.adk.markdownnoteapp.model.UserFile;
import com.adk.markdownnoteapp.repo.FileRepo;
import com.adk.markdownnoteapp.repo.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IFileService} interface
 */
@Service
public class FileService implements IFileService {

    private final String languageToolURL;
    private final ObjectMapper objectMapper;
    private final FileRepo fileRepo;
    private final UserRepo userRepo;

    @Autowired
    FileService(Environment env, ObjectMapper objectMapper, FileRepo fileRepo, UserRepo userRepo){
        this.objectMapper = objectMapper;
        languageToolURL = env.getProperty("language-tool-api.url");
        this.fileRepo = fileRepo;
        this.userRepo = userRepo;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllFileIdsForUser(String userId){
        Optional<List<UserFile>> files = fileRepo.findByUserId(userId);

        if(files.isEmpty())
            throw new EntityNotFoundException(UserFile.class, "user.id", userId);
        List<String> fileIds = new ArrayList<>();
        files.get().forEach(file ->  fileIds.add(file.getId()));
        return fileIds;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LanguageDTO> getSupportedLanguages() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(languageToolURL + "/v2/languages"))
                .method("GET", HttpRequest.BodyPublishers.noBody()).build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            return objectMapper.readValue(response.body(),  objectMapper.getTypeFactory().constructCollectionType(List.class, LanguageDTO.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GrammarCheckDTO checkGrammar(File file, String language) {

        Map<String, String> params = new HashMap<>();
        StringBuilder fileText = new StringBuilder();
        params.put("language", language);
        //breaks up html data into text and markup
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            while (line != null) {
                int i = 0;
                while( i < line.length()){
                    String subString = line.substring(i);
                    int markupStart = subString.indexOf("<");
                    int markupEnd = subString.indexOf(">");
                    if(markupStart == -1 || markupEnd == -1){ // A complete tag doesn't exist on this line
                        fileText.append(subString);
                        i = line.length();
                    } else if ( markupStart < markupEnd){// both beginning and end brackets exist
                        if(markupStart != 0){
                            fileText.append(subString.substring(0, markupStart));
                            i += markupStart;
                        } else {
                            i += markupEnd + 1;
                        }
                    } else {
                        i = line.length();
                    }
                }
                fileText.append("\n");
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        params.put("text", fileText.toString());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create(languageToolURL + "/v2/check"))
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .POST(getParamsUrlEncoded(params))
                .build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return  objectMapper.readValue(response.body().toString(), GrammarCheckDTO.class);
        } catch (IOException | InterruptedException e) {
            throw new ThirdPartyAPIException("Language Tool");
        }
    }

    /**
     * Converts a MarkdownFile into HTML
     * @param file {@link MultipartFile} markdown file to be converted
     * @return {@link Byte[]} the data for the converted html file
     */
    private byte[] convertToHtml(MultipartFile file) {
        Parser parser = Parser.builder().build();
        Node document = null;
        try {
            document = parser.parse(new String(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document).getBytes();
    }

    /**
     * Encodes the paramaters into the url for a request to Language Tool API
     * @param parameters Map of key {@link String} and value {@link String} containing the parameter values
     * @return {@link HttpRequest.BodyPublisher}
     */
    private HttpRequest.BodyPublisher getParamsUrlEncoded(Map<String, String> parameters) {
        String urlEncoded = parameters.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
        return HttpRequest.BodyPublishers.ofString(urlEncoded);
    }
}
