package com.adk.markdownnoteapp.controller;

import com.adk.markdownnoteapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create/{username}")
    public ResponseEntity<Object> createUser(@PathVariable String username) {
        return new ResponseEntity<>(userService.createUser(username), HttpStatus.OK);
    }

    @GetMapping("/getUserId/{username}")
    public ResponseEntity<Object> getUserId(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserId(username), HttpStatus.OK);
    }
}
