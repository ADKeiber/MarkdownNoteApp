package com.adk.markdownnoteapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WarningDTO implements Serializable {
    @JsonProperty("incompleteResults")
    private boolean incompleteResults;
}
