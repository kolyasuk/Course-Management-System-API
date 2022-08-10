package edu.sombra.cms.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class Student extends Owners implements EntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(name = "group_name")
    private String group;

    private String faculty;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<StudentCourse> studentCourses = new ArrayList<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<StudentLesson> studentLessons = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public List<Course> getActiveCourseList(){
        return studentCourses.stream().map(StudentCourse::getCourse).filter(Course::isActive).collect(Collectors.toList());
    }

    @Override
    public List<Long> getOwnersIds() {
        return Collections.singletonList(user.getId());
    }
}
