package edu.sombra.cms.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String group;
    private String faculty;

}
