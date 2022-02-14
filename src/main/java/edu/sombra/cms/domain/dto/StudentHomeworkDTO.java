package edu.sombra.cms.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentHomeworkDTO {

    private StudentOverviewDTO student;

    private String notes;
    private String feedback;
    private Integer mark;
    private String fileKey;
    private String filename;

}
