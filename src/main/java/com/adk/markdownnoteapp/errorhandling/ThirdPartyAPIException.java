package com.adk.markdownnoteapp.errorhandling;

/**
 *
 */
public class ThirdPartyAPIException extends RuntimeException {
    public ThirdPartyAPIException(String apiName) {
        super("The 3rd part API '" + apiName + "' had an error and its call failed...");
    }
}
