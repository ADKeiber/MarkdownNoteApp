package com.adk.markdownnoteapp.service;

public interface IUserService {
    String getUserId(String username);
    String createUser(String username);
}
