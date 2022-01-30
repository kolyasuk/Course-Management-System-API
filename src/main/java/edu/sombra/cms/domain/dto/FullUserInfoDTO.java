package edu.sombra.cms.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullUserInfoDTO {

    private long id;
    private String username;
    private String email;
    private String fullName;

}
