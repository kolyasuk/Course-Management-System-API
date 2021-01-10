package edu.sombra.cms.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
public class UserDTO {

    private Integer id;

    private String username;
    private String fullName;
    private String password;
    private String rePassword;
    private Set<String> authorities;
}
