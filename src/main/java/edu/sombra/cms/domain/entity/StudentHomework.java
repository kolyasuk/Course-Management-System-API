package edu.sombra.cms.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class StudentHomework implements Serializable {

    @EmbeddedId
    private StudentHomeworkPK id;

    @ManyToOne
    @MapsId("student_id")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("homework_id")
    @JoinColumn(name = "homework_id")
    private Homework homework;
}
