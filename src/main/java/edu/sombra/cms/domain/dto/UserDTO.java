package edu.sombra.cms.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private long id;
    private String email;
    private String fullName;
    private String role;
}
