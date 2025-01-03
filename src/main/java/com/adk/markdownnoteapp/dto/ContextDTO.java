package com.adk.markdownnoteapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * DTO containing the context in a {@link MatchDTO}
 */
public class ContextDTO implements Serializable {

    @JsonProperty("text")
    private String text;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("length")
    private Integer length;
}