package edu.sombra.cms.domain.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
public class Homework {

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lesson;

}
