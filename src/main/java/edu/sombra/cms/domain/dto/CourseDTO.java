package edu.sombra.cms.domain.dto;

import edu.sombra.cms.domain.enumeration.CourseStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseDTO {

    private long id;
    private String name;
    private String description;
    private CourseStatus status;
    private List<OverviewInstructorDTO> instructors;
    private List<LessonDTO> lessons;

}
