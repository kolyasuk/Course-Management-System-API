package edu.sombra.cms.domain.dto;

import edu.sombra.cms.domain.enumeration.CourseOverviewStatus;
import edu.sombra.cms.domain.enumeration.CourseStatus;

public interface ICourseOverview {

    Long getId();
    String getName();
    CourseStatus getStatus();

    default CourseOverviewStatus getCourseOverviewStatus(){
        return CourseOverviewStatus.of(getStatus());
    }
}
