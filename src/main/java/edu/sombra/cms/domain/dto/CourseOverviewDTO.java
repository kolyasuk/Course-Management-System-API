package edu.sombra.cms.domain.dto;

import edu.sombra.cms.domain.enumeration.CourseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseOverviewDTO {

    private long id;
    private String name;
    //todo: add dto for status
    private CourseStatus status;

}
