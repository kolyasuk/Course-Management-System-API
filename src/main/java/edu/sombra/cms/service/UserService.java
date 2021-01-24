package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.RegistrationDTO;
import edu.sombra.cms.domain.payload.UserView;

public interface UserService {

    UserView create(RegistrationDTO registrationDTO);

    UserView findUserById(long userId);
}
