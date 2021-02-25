package edu.sombra.cms.domain.payload;

import edu.sombra.cms.domain.enumeration.RoleEnum;
import lombok.Data;

@Data
public class UserView {

    private Integer id;
    private String username;
    private String email;
    private String fullName;
    private String message;
    private RoleEnum requestedRole;
}
