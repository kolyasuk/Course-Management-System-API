package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.RegistrationDTO;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.enumeration.RoleEnum;
import edu.sombra.cms.domain.mapper.UserMapper;
import edu.sombra.cms.domain.payload.UserView;
import edu.sombra.cms.repository.UserRepository;
import edu.sombra.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserView create(RegistrationDTO registrationDTO) {
        validateCreatingAdminRole(registrationDTO);

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

        return userMapper.toView(userRepository.save(user));
    }

    @Override
    public UserView findUserById(long userId) {
        return null;
    }

    private void validateCreatingAdminRole(RegistrationDTO registrationDTO){
        if(!registrationDTO.getRoles().contains(RoleEnum.ROLE_ADMIN)) return;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null
                && !authentication.getAuthorities().contains(new SimpleGrantedAuthority(RoleEnum.ROLE_ADMIN.toString())))
            throw new AccessDeniedException("You are not allowed to create admins");
    }
}
