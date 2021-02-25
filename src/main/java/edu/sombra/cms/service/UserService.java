package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.RegistrationDTO;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.enumeration.RoleEnum;
import edu.sombra.cms.domain.payload.UserView;

import java.util.List;

public interface UserService {

    UserView create(RegistrationDTO registrationDTO);

    void assignUserRole(Integer userId, RoleEnum roleEnum);

    User findUserById(Integer userId);

    List<UserView> findUsersByRole(RoleEnum role);
}
