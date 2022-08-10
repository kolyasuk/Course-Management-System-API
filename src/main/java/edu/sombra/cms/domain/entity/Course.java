package edu.sombra.cms.domain.entity;

import edu.sombra.cms.domain.enumeration.CourseStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static edu.sombra.cms.util.constants.SystemSettings.MINIMUM_NUMBER_OF_COURSE_INSTRUCTORS;
import static edu.sombra.cms.util.constants.SystemSettings.MINIMUM_NUMBER_OF_COURSE_LESSONS;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
public class Course extends Owners {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "course", fetch = LAZY)
    private Set<StudentCourse> studentCourses = new HashSet<>();

    @OneToMany(mappedBy = "course", fetch = LAZY)
    private List<Lesson> lessons = new ArrayList<>();

    @ManyToMany(fetch = LAZY)
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

    public List<User> getRelatedUsers(){
        var users = instructors.stream().map(Instructor::getUser).collect(Collectors.toList());

        users.addAll(getStudents().stream().map(Student::getUser).collect(Collectors.toList()));
        return users;
    }

    public boolean isActive(){
        return status.equals(CourseStatus.ACTIVE);
    }

    public boolean canBeActivated(){
        return instructors.size() >= MINIMUM_NUMBER_OF_COURSE_INSTRUCTORS && lessons.size() >= MINIMUM_NUMBER_OF_COURSE_LESSONS;
    }

    @Override
    public List<Long> getOwnersIds() {
        return instructors.stream().flatMap(i -> i.getOwnersIds().stream()).collect(Collectors.toList());
    }
}
