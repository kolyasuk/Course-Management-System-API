package edu.sombra.cms.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class StudentLessonPK implements Serializable {

    @Column(name = "student_id")
    private long studentId;

    @Column(name = "lesson_id")
    private long lessonId;

    public StudentLessonPK(Student student, Lesson lesson) {
        this.studentId = student.getId();
        this.lessonId = lesson.getId();
    }
}
