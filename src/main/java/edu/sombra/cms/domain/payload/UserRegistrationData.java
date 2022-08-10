package edu.sombra.cms.domain.payload;

import edu.sombra.cms.domain.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationData {

    @Size(min = 6, max = 40, message = "{registrationdata.password.size}")
    private String password;

    @Size(min = 2, max = 50, message = "{registrationdata.fullName.size}")
    private String fullName;

    @Email(message = "{registrationdata.email.email}")
    @NotBlank(message = "{registrationdata.email.size}")
    @Size(max = 50, message = "{registrationdata.email.blank}")
    private String email;

    @NotNull(message = "{registrationdata.role.null}")
    private Role role;
}
