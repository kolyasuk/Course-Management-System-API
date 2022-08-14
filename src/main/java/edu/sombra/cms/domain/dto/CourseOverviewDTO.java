package edu.sombra.cms.domain.dto;

import edu.sombra.cms.domain.enumeration.CourseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseOverviewDTO implements ICourseOverview {

    private Long id;
    private String name;
    private CourseStatus status;

}
