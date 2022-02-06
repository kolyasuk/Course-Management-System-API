package edu.sombra.cms.domain.dto;

import edu.sombra.cms.domain.enumeration.CourseStatus;
import edu.sombra.cms.domain.enumeration.StudentCourseStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentCourseDTO {

    private long id;
    private String name;
    //todo: add dto for status
    private Integer mark;
    private CourseStatus courseStatus;
    private StudentCourseStatus passStatus;
    private List<StudentLessonOverviewDTO> studentLessons;

}
