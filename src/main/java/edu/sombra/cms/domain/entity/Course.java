package edu.sombra.cms.domain.entity;

import edu.sombra.cms.domain.enumeration.CourseStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static edu.sombra.cms.util.constants.SystemSettings.MINIMUM_NUMBER_OF_COURSE_INSTRUCTORS;
import static edu.sombra.cms.util.constants.SystemSettings.MINIMUM_NUMBER_OF_COURSE_LESSONS;

@Getter
@Setter
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 120)
    private String name;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private Set<StudentCourse> studentCourses = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "instructor_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id"))
    private Set<Instructor> instructors = new HashSet<>();

    public void addInstructor(Instructor instructor){
        instructors.add(instructor);
    }

    public List<Student> getStudents(){
        return studentCourses.stream().map(StudentCourse::getStudent).collect(Collectors.toList());
    }

    public List<User> getInstructorUsers(){
        return instructors.stream().map(Instructor::getUser).collect(Collectors.toList());
    }

    public boolean isActive(){
        return status.equals(CourseStatus.ACTIVE);
    }

    public boolean canBeActivated(){
        return instructors.size() >= MINIMUM_NUMBER_OF_COURSE_INSTRUCTORS && lessons.size() >= MINIMUM_NUMBER_OF_COURSE_LESSONS;
    }

}
