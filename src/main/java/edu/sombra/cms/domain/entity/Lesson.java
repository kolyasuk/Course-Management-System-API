package edu.sombra.cms.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Type(type="text")
    private String description;

    private String homework;

    private LocalDate date;

    @OneToMany(mappedBy = "lesson")
    private List<StudentLesson> studentLessons = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="course_id", nullable = false)
    private Course course;

    public List<User> getRelatedUsers(){
        return course.getRelatedUsers();
    }

}
