package com.adk.markdownnoteapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.io.File;

@Entity
@Table(name = "User")
@Data @NoArgsConstructor
@AllArgsConstructor
public class UserFile {

    @Id
    @UuidGenerator
    private String id;
    private File file;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false, updatable=false)
    private UserEntity user;
}
