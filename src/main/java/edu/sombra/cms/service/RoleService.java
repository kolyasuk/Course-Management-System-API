package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.RegistrationDTO;
import edu.sombra.cms.domain.entity.Role;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.enumeration.RoleEnum;
import edu.sombra.cms.domain.payload.UserView;

import java.util.List;

public interface RoleService {

    Role findRoleByName(RoleEnum roleEnum);

}
