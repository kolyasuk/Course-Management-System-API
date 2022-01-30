package edu.sombra.cms.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class StudentCoursePK implements Serializable {

    @Column(name = "student_id")
    private long studentId;

    @Column(name = "course_id")
    private long courseId;

    public StudentCoursePK(Student student, Course course) {
        this.studentId = student.getId();
        this.courseId = course.getId();
    }
}
