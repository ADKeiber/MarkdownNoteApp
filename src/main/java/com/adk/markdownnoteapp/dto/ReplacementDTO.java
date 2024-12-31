package com.adk.markdownnoteapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * DTO containing possible replacement values for a {@link MatchDTO}
 */
public class ReplacementDTO implements Serializable {
    @JsonProperty("value")
    private String value;
}