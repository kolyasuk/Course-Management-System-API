package edu.sombra.cms.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class StudentCourse implements Serializable {

    @EmbeddedId
    private StudentCoursePK id;

    @ManyToOne
    @MapsId("student_id")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("course_id")
    @JoinColumn(name = "course_id")
    private Course course;

    private Integer mark;

    public StudentCourse(Student student, Course course) {
        this.id = new StudentCoursePK(student, course);
        this.student = student;
        this.course = course;
    }
}
