package com.adk.markdownnoteapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class UserEntity {
    @Id
    String id;
    String username;
}
