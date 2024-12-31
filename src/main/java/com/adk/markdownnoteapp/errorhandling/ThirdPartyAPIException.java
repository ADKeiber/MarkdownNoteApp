package com.adk.markdownnoteapp.errorhandling;

/**
 * Exception for third party APIs. Very generic exception used when failure is due to 3rd party
 */
public class ThirdPartyAPIException extends RuntimeException {
    public ThirdPartyAPIException(String apiName) {
        super("The 3rd part API '" + apiName + "' had an error and its call failed...");
    }
}
