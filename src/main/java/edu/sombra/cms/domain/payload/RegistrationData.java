package edu.sombra.cms.domain.payload;

import edu.sombra.cms.domain.enumeration.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegistrationData {

    @NotBlank(message = "{registrationdata.username.blank}")
    @Size(min = 3, max = 20, message = "{registrationdata.username.size}")
    private String username;

    @NotBlank(message = "{registrationdata.password.blank}")
    @Size(min = 6, max = 40, message = "{registrationdata.password.size}")
    private String password;

    @NotBlank(message = "{registrationdata.fullName.blank}")
    @Size(min = 2, max = 50, message = "{registrationdata.fullName.size}")
    private String fullName;

    @Email(message = "{registrationdata.email.email}")
    @NotBlank(message = "{registrationdata.email.size}")
    @Size(max = 50, message = "{registrationdata.email.blank}")
    private String email;

    @NotNull(message = "{registrationdata.role.null}")
    private RoleEnum role;
}
