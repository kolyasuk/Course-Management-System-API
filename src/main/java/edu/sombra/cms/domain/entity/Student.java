package edu.sombra.cms.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 40)
    private String firstName;

    @NotBlank
    @Size(max = 40)
    private String lastName;

    @Email
    @Size(max = 50)
    private String email;

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

}
