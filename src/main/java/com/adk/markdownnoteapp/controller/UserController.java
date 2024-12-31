package com.adk.markdownnoteapp.controller;

import com.adk.markdownnoteapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.adk.markdownnoteapp.errorhandling.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller containing endpoints to interact with users
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Creates a new user
     * @param username {@link String} username of the new user
     * @return {@link ResponseEntity} containing the userId if no exceptions thrown, {@link ApiError} otherwise
     */
    @PostMapping("/create/{username}")
    public ResponseEntity<Object> createUser(@PathVariable String username) {
        return new ResponseEntity<>(userService.createUser(username), HttpStatus.OK);
    }

    /**
     * Gets the User ID of an existing user
     * @param username {@link String} of the user's username
     * @return {@link ResponseEntity} containing the userId if no exceptions thrown, {@link ApiError} otherwise
     */
    @GetMapping("/getUserId/{username}")
    public ResponseEntity<Object> getUserId(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserId(username), HttpStatus.OK);
    }
}
