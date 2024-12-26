package com.adk.markdownnoteapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TypeDTO implements Serializable {
    @JsonProperty("typename")
    private String typeName;
}