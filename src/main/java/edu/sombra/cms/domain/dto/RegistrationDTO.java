package edu.sombra.cms.domain.dto;

import edu.sombra.cms.domain.enumeration.RoleEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class RegistrationDTO {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(min = 3, max = 50)
    private String fullName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 200)
    private String message;

    @NotBlank
    @Size(max = 200)
    @Enumerated(EnumType.STRING)
    private RoleEnum requestedRole;

    @NotBlank
    @Size(min = 6, max = 40)
    private String rePassword;

    @NotNull
    private Set<RoleEnum> roles;
}
