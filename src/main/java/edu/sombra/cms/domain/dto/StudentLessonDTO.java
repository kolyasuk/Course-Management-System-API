package edu.sombra.cms.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentLessonDTO {

    private Integer mark;
    private String notes;
    private String feedback;
    private LessonDTO lesson;

}
