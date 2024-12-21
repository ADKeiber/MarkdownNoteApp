package com.adk.markdownnoteapp.service;

import com.adk.markdownnoteapp.errorhandling.EntityNotFoundException;
import com.adk.markdownnoteapp.errorhandling.UsernameAlreadyExistsException;
import com.adk.markdownnoteapp.model.UserEntity;
import com.adk.markdownnoteapp.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service @Slf4j
public class UserService implements IUserService {

    private UserRepo userRepo;

    @Override
    public String getUserId(String username) {
        Optional<UserEntity> user = userRepo.findByUsername(username);
        if(user.isEmpty())
            throw new EntityNotFoundException(UserEntity.class, "username", username);
        return  user.get().getId();
    }

    @Override
    public String createUser(String username) {
        if(userRepo.existsByUsername(username))
            throw new UsernameAlreadyExistsException(username);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        UserEntity returnedEntity = userRepo.save(userEntity);
        return returnedEntity.getId();
    }
}
