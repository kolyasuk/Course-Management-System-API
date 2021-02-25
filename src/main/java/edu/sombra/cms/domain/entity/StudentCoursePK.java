package edu.sombra.cms.domain.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StudentCoursePK implements Serializable {

    @Column(name = "student_id")
    private long studentId;

    @Column(name = "course_id")
    private long courseId;

}
