package edu.sombra.cms.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StudentLesson extends Owners implements Serializable {

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "homework_file",
            joinColumns = {
                    @JoinColumn(name="lesson_id"),
                    @JoinColumn(name="student_id")
            },
            inverseJoinColumns = @JoinColumn(name="homework_file_id"))
    private Set<S3File> homeworkFiles = new HashSet<>();

    private Integer mark;

    private String notes;

    private String feedback;

    public void addHomework(S3File s3File){
        homeworkFiles.add(s3File);
    }

    public StudentLesson(Student student, Lesson lesson) {
        this.id = new StudentLessonPK(student, lesson);
        this.student = student;
        this.lesson = lesson;
    }

    @Override
    public List<Long> getOwnersIds() {
        return concat(lesson.getOwnersIds().stream(), student.getOwnersIds().stream()).collect(toList());
    }

    public boolean canBeEvaluated() {
        return !homeworkFiles.isEmpty() || LocalDate.now().isAfter(lesson.getFinishDate());
    }

    @Data
    @Embeddable
    @NoArgsConstructor
    public static class StudentLessonPK implements Serializable {

        @Column(name = "student_id")
        private long studentId;

        @Column(name = "lesson_id")
        private long lessonId;

        public StudentLessonPK(Student student, Lesson lesson) {
            this.studentId = student.getId();
            this.lessonId = lesson.getId();
        }
    }
}
