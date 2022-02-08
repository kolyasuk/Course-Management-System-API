package edu.sombra.cms.domain.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class InstructorData {

    @NotBlank(message = "{instructordata.firstName.blank}")
    @Size(max = 40, message = "{instructordata.firstName.size}")
    private String firstName;

    @NotBlank(message = "{instructordata.lastName.blank}")
    @Size(max = 40, message = "{instructordata.lastName.size}")
    private String lastName;

    @Email
    @NotBlank(message = "{instructordata.email.blank}")
    @Size(max = 40, message = "{instructordata.email.size}")
    private String email;

    @NotBlank(message = "{instructordata.info.blank}")
    @Size(max = 500, message = "{instructordata.info.size}")
    private String info;

}
