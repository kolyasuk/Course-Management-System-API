package edu.sombra.cms.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "s3_file")
public class S3File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileKey;

    @OneToOne
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "s3File")
    private StudentLesson homework;

}
