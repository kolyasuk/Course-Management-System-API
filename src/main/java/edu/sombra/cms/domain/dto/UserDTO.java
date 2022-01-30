package edu.sombra.cms.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private long id;

    private String username;
    private String fullName;
    private String password;
    private String rePassword;
    private Set<String> authorities;
}
