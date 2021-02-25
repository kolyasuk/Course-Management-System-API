package edu.sombra.cms.domain.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 120)
    private String name;

    @NotBlank
    @Size(max = 50000)
    @Type(type="text")
    private String description;

    @ManyToOne
    @JoinColumn(name="course_id", nullable = false)
    private Course course;

    @OneToOne(mappedBy = "lesson")
    private Homework homework;

}
