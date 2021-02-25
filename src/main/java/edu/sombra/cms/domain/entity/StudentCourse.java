package edu.sombra.cms.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class StudentCourse implements Serializable {

    @EmbeddedId
    private StudentCoursePK id;

    @ManyToOne
    @MapsId("student_id") //This is the name of attr in EmployerDeliveryAgentPK class
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("course_id")
    @JoinColumn(name = "course_id")
    private Course course;
}
