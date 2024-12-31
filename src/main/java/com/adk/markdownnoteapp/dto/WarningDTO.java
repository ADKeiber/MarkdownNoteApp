package com.adk.markdownnoteapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * DTO containing the warnings of a Grammar Check
 */
public class WarningDTO implements Serializable {
    @JsonProperty("incompleteResults")
    private boolean incompleteResults;
}
