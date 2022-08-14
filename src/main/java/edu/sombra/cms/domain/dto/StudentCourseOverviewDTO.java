package edu.sombra.cms.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.sombra.cms.domain.enumeration.CourseOverviewStatus;
import edu.sombra.cms.domain.enumeration.CourseStatus;
import edu.sombra.cms.domain.enumeration.StudentCourseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourseOverviewDTO implements ICourseOverview {

    private Long id;
    private String name;
    private Integer mark;
    private CourseStatus status;
    private StudentCourseStatus passStatus;

    @JsonIgnore
    public CourseOverviewStatus getCourseOverviewStatus(){
        if(passStatus != null)
            return CourseOverviewStatus.of(passStatus);

        return CourseOverviewStatus.of(status);
    }
}
