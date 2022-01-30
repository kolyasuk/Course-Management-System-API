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

    @NotBlank(message = "username should not be null")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "password should not be null")
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank(message = "fullName should not be null")
    @Size(min = 3, max = 50)
    private String fullName;

    @NotBlank(message = "email should not be null")
    @Size(max = 50)
    @Email
    private String email;

    @NotNull
    private RoleEnum role;
}
