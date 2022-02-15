package edu.sombra.cms.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
public class StudentLesson implements Serializable {

    @EmbeddedId
    private StudentLessonPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("student_id")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("lesson_id")
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "homework_file_id", referencedColumnName = "id")
    private S3File homeworkFile;

    private Integer mark;

    private String notes;

    private String feedback;

    public StudentLesson(Student student, Lesson lesson) {
        this.id = new StudentLessonPK(student, lesson);
        this.student = student;
        this.lesson = lesson;
    }
}
