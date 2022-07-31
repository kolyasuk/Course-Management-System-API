package edu.sombra.cms.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Lesson extends Owners implements EntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String description;

    private String homework;

    @Column(name = "finish_date")
    private LocalDate finishDate;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
    private List<StudentLesson> studentLessons = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="instructor_id", nullable = false)
    private Instructor instructor;

    public List<User> getRelatedUsers(){
        return course.getRelatedUsers();
    }

    @Override
    public List<Long> getOwnersIds() {
        return course.getOwnersIds();
    }
}
