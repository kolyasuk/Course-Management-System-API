package edu.sombra.cms.service;

import edu.sombra.cms.domain.entity.Role;
import edu.sombra.cms.domain.enumeration.RoleEnum;
import edu.sombra.cms.messages.SomethingWentWrongException;

public interface RoleService {

    Role findRoleByName(RoleEnum roleEnum) throws SomethingWentWrongException;

}
