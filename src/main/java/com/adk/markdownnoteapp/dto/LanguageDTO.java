package com.adk.markdownnoteapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class LanguageDTO implements Serializable {

    @JsonProperty("name")
    private String name;

    @JsonProperty("code")
    private String code;

    @JsonProperty("longCode")
    private String longCode;

}