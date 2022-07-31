package edu.sombra.cms.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class StudentCourse extends Owners implements Serializable {

    @EmbeddedId
    private StudentCoursePK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("student_id")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("course_id")
    @JoinColumn(name = "course_id")
    private Course course;

    private Integer mark;

    private String feedback;

    public StudentCourse(Student student, Course course) {
        this.id = new StudentCoursePK(student, course);
        this.student = student;
        this.course = course;
    }

    @Override
    public List<Long> getOwnersIds() {
        return student.getOwnersIds();
    }

    @Data
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StudentCoursePK implements Serializable {

        @Column(name = "student_id")
        private long studentId;

        @Column(name = "course_id")
        private long courseId;

        public StudentCoursePK(Student student, Course course) {
            this.studentId = student.getId();
            this.courseId = course.getId();
        }
    }
}
