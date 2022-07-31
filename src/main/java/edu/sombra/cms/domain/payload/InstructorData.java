package edu.sombra.cms.domain.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class InstructorData {

    @NotBlank(message = "{instructordata.firstName.blank}")
    @Size(max = 40, message = "{instructordata.firstName.size}")
    private String firstName;

    @NotBlank(message = "{instructordata.lastName.blank}")
    @Size(max = 40, message = "{instructordata.lastName.size}")
    private String lastName;

    @NotBlank(message = "{instructordata.info.blank}")
    @Size(max = 500, message = "{instructordata.info.size}")
    private String info;

}
