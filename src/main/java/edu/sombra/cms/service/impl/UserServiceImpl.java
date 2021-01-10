package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.RegistrationDTO;
import edu.sombra.cms.domain.dto.UserDTO;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.mapper.UserMapper;
import edu.sombra.cms.domain.payload.UserView;
import edu.sombra.cms.repository.UserRepository;
import edu.sombra.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import javax.validation.ValidationException;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserView create(RegistrationDTO registrationDTO) {
        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            throw new ValidationException("Username exists!");
        }
        if (!registrationDTO.getPassword().equals(registrationDTO.getRePassword())) {
            throw new ValidationException("Passwords don't match!");
        }
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new ValidationException("Email is already in use!");
        }

        User user = userMapper.fromRegistrationDTO(registrationDTO);

        user = userRepository.save(user);

        return userMapper.toView(user);
    }
}
