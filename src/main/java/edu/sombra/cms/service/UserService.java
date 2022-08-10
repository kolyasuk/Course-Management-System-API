package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.FullUserInfoDTO;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.enumeration.Role;
import edu.sombra.cms.domain.payload.UserRegistrationData;
import edu.sombra.cms.messages.SomethingWentWrongException;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

    FullUserInfoDTO create(@Valid UserRegistrationData userRegistrationData) throws SomethingWentWrongException;

    void setUserRole(Long userId, Role role) throws SomethingWentWrongException;

    User findUserById(Long userId) throws SomethingWentWrongException;

    List<FullUserInfoDTO> findUsersByRole(Role role) throws SomethingWentWrongException;

    User getLoggedUser() throws SomethingWentWrongException;
}
