package com.adk.markdownnoteapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

/**
 * Database entity used to hold user information
 */
@Entity
@Table(name = "MarkdownNoteUser")
@Data @NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @UuidGenerator
    private String id;
    private String username;
}
