package com.adk.markdownnoteapp.service;

import com.adk.markdownnoteapp.errorhandling.EntityNotFoundException;
import com.adk.markdownnoteapp.model.FileType;
import com.adk.markdownnoteapp.model.UserEntity;
import com.adk.markdownnoteapp.model.UserFile;
import com.adk.markdownnoteapp.repo.FileRepo;
import com.adk.markdownnoteapp.repo.UserRepo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class FileService implements IFileService {

    @Autowired
    FileRepo fileRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public String uploadFile(MultipartFile file, String userId) {
        Optional<UserEntity> userOptional = userRepo.findById(userId);

        if(userOptional.isEmpty())
            throw new EntityNotFoundException(UserEntity.class, "id", userId);

        UserFile userFile = new UserFile();
        try {
            userFile.setData(file.getBytes());
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
            case FileType.TXT -> fileOptional.get().getId() + ".txt";
            case FileType.HTML -> fileOptional.get().getId() + ".html";
            default -> throw new IllegalStateException("Unexpected value: " + fileType);
        };
        File file = new File(fileName);
        try {
            FileUtils.writeByteArrayToFile(file, fileOptional.get().getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }
}
