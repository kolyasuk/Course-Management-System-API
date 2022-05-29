package edu.sombra.cms.domain.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginRequestData {

    @NotNull(message = "{loginrequestdata.email.null}")
    private String email;
    @NotNull(message = "{loginrequestdata.password.null}")
    private String password;

}
