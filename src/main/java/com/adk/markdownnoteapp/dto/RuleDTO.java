package com.adk.markdownnoteapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * DTO containing the rule for a specific {@link MatchDTO}
 */
public class RuleDTO implements Serializable {

    @JsonProperty("id")
    private String id;

    @JsonProperty("subId")
    private String subId;

    @JsonProperty("sourceFile")
    private String sourceFile;

    @JsonProperty("description")
    private String description;

    @JsonProperty("issueType")
    private String issueType;

    @JsonProperty("category")
    private CategoryDTO category;

    @JsonProperty("confidence")
    private String confidence;
}