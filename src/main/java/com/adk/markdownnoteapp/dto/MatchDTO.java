package com.adk.markdownnoteapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class MatchDTO implements Serializable  {

    @JsonProperty("message")
    private String message;

    @JsonProperty("shortmessage")
    private String shortMessage;

    @JsonProperty("replacements")
    private List<ReplacementDTO> replacements;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("length")
    private Integer length;

    @JsonProperty("context")
    private ContextDTO context;

    @JsonProperty("sentence")
    private String sentence;

    @JsonProperty("type")
    private TypeDTO type;

    @JsonProperty("rule")
    private RuleDTO rule;

    @JsonProperty("ignoreForIncompleteSentence")
    private Boolean ignoreForIncompleteSentence;

    @JsonProperty("contextForSureMatch")
    private String contextForSureMatch;
}