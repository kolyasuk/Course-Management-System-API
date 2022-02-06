package edu.sombra.cms.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LessonDTO {

    private long id;
    private String name;
    private String description;
    private String homework;

    private List<StudentHomeworkDTO> studentHomework;

}
