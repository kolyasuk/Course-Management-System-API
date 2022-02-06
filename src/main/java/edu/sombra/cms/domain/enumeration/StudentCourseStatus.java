package edu.sombra.cms.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StudentCourseStatus {

    PASSED("PASSED"),
    FAILED("FAILED");

    private final String name;

    public static StudentCourseStatus ofMark(Integer mark){
        return mark >= GradingSystem.PERCENTAGE.getPassRate() ? PASSED : FAILED;
    }

}
