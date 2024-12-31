package com.adk.markdownnoteapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

/**
 * Database entity used to hold information about a user's file
 */
@Entity
@Table(name = "MarkdownFile")
@Data @NoArgsConstructor
@AllArgsConstructor
public class UserFile {

    @Id
    @UuidGenerator
    private String id;

    @Lob
    private byte[] markdownData;

    @Lob
    private byte[] htmlData;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false, updatable=false)
    private UserEntity user;

}
