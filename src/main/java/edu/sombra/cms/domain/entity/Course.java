package edu.sombra.cms.domain.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 120)
    private String name;

    @OneToMany(mappedBy = "student")
    private Set<StudentCourse> studentCourses = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "instructor_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id"))
    Set<Instructor> instructors;

}
