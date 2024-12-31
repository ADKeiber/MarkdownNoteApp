package com.adk.markdownnoteapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * DTO containing a grammar check for a file
 */
public class GrammarCheckDTO  implements Serializable {

    @JsonProperty("warnings")
    private WarningDTO warning;

    @JsonProperty("matches")
    private List<MatchDTO> matches;
}
