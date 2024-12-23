package com.adk.markdownnoteapp.service;

import com.adk.markdownnoteapp.errorhandling.EntityNotFoundException;
import com.adk.markdownnoteapp.model.FileType;
import com.adk.markdownnoteapp.model.UserEntity;
import com.adk.markdownnoteapp.model.UserFile;
import com.adk.markdownnoteapp.repo.FileRepo;
import com.adk.markdownnoteapp.repo.UserRepo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
        return null;
    }
}
