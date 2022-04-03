package edu.sombra.cms.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentHomeworkDTO {

    private StudentOverviewDTO student;

    private String notes;
    private String feedback;
    private Integer mark;
    private List<FileDTO> homeworks;

}
