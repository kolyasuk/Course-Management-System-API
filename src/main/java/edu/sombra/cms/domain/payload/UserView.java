package edu.sombra.cms.domain.payload;

import lombok.Data;

@Data
public class UserView {

    private Integer id;
    private String username;
    private String email;
    private String fullName;
}
