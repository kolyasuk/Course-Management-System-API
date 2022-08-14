package edu.sombra.cms.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CourseOverviewStatus {

    INACTIVE("INACTIVE"),
    ACTIVE("ACTIVE"),
    FINISHED("FINISHED"),
    PASSED("PASSED"),
    FAILED("FAILED");

    private final String name;

    public static CourseOverviewStatus of(StudentCourseStatus studentCourseStatus) {
        return CourseOverviewStatus.valueOf(studentCourseStatus.name());
    }

    public static CourseOverviewStatus of(CourseStatus status) {
        return CourseOverviewStatus.valueOf(status.name());
    }

}
