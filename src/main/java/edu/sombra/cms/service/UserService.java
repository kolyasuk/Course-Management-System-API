package edu.sombra.cms.service;

import edu.sombra.cms.domain.dto.RegistrationDTO;
import edu.sombra.cms.domain.dto.UserDTO;
import edu.sombra.cms.domain.payload.UserView;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserView create(RegistrationDTO registrationDTO);
}
