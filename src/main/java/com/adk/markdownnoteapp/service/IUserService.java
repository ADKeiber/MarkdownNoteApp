package com.adk.markdownnoteapp.service;

/**
 * Interface containing methods required to interact with users
 */
public interface IUserService {

    /**
     * Gets a userID that is associated with the given username
     * @param username {@link String} username of the account
     * @return {@link String} the userID
     */
    String getUserId(String username);

    /**
     * Creates a new user
     * @param username {@link String} username of the new user
     * @return {@link String} the id of the newly created user
     */
    String createUser(String username);
}
