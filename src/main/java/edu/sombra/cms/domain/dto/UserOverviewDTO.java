package edu.sombra.cms.domain.dto;

import edu.sombra.cms.domain.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserOverviewDTO {

    private long id;
    private String fullName;
    private Role role;
}
