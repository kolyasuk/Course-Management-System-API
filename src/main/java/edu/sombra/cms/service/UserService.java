package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.FullUserInfoDTO;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.enumeration.RoleEnum;
import edu.sombra.cms.domain.payload.RegistrationData;

import java.util.List;

public interface UserService {

    FullUserInfoDTO create(RegistrationData registrationData);

    void setUserRole(Long userId, RoleEnum roleEnum);

    User findUserById(Long userId);

    List<FullUserInfoDTO> findUsersByRole(RoleEnum role);

    User getLoggedUser();
}
