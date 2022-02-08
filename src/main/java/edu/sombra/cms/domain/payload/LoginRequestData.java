package edu.sombra.cms.domain.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginRequestData {

    @NotNull(message = "{loginrequestdata.username.null}")
    private String username;
    @NotNull(message = "{loginrequestdata.password.null}")
    private String password;

}
