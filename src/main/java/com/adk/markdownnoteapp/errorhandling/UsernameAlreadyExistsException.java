package com.adk.markdownnoteapp.errorhandling;

/**
 * Exception that handles a username already existing inside of a database
 */
public class UsernameAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1383744788001415406L;

    public UsernameAlreadyExistsException(String username) {
        super("The username '" + username + "' exists already!");
    }
}