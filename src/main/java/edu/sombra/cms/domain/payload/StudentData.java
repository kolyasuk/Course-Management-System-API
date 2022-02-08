package edu.sombra.cms.domain.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class StudentData {

    @NotBlank(message = "{studentdata.firstName.blank}")
    @Size(max = 40, message = "{studentdata.firstName.size}")
    private String firstName;

    @NotBlank(message = "{studentdata.lastName.blank}")
    @Size(max = 40, message = "{studentdata.lastName.size}")
    private String lastName;

    @Email(message = "{studentdata.email.email}")
    @NotBlank(message = "{studentdata.email.blank}")
    @Size(max = 40, message = "{studentdata.email.size}")
    private String email;

}
