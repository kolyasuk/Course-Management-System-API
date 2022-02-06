package edu.sombra.cms.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentLessonOverviewDTO {

    private Integer mark;
    private LessonOverviewDTO lesson;

}
