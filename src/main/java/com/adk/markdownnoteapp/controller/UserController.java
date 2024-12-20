package com.adk.markdownnoteapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    @PostMapping("/create/{username}")
    public ResponseEntity<Object> createUser(@RequestParam String username) {
        return new ResponseEntity<>("It Worked", HttpStatus.OK);
    }

    @GetMapping("/getUserId/{username}")
    public ResponseEntity<Object> getUserId(@RequestParam String username) {
        return new ResponseEntity<>("It Worked", HttpStatus.OK);
    }
}