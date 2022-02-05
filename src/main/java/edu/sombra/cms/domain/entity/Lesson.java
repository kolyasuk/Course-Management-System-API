package edu.sombra.cms.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Type(type="text")
    private String description;

    private String homework;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name="course_id", nullable = false)
    private Course course;

}
