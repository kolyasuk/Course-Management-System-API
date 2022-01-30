package edu.sombra.cms.service.impl;

import edu.sombra.cms.config.security.UserDetailsImpl;
import edu.sombra.cms.domain.dto.FullUserInfoDTO;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.enumeration.RoleEnum;
import edu.sombra.cms.domain.mapper.UserMapper;
import edu.sombra.cms.domain.payload.RegistrationData;
import edu.sombra.cms.repository.UserRepository;
import edu.sombra.cms.service.RoleService;
import edu.sombra.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static edu.sombra.cms.domain.enumeration.RoleEnum.ROLE_ADMIN;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

    @Override
    public FullUserInfoDTO create(RegistrationData registrationData) {
        validateRegistrationData(registrationData);

        User userToRegister = userMapper.fromRegistrationData(registrationData);
        var registeredUser = userMapper.toView(userRepository.save(userToRegister));

        sendWelcomeEmail(registeredUser.getEmail());

        return registeredUser;
    }

    private void sendWelcomeEmail(String email){
        System.out.println("Send message on mail to complete the registration");
    }

    private void validateRegistrationData(RegistrationData registrationData) {
        if (userRepository.existsByUsername(registrationData.getUsername())) {
            //todo: refactor to keeps exceptions in files
            throw new ValidationException("Username exists!");
        }
        if (userRepository.existsByEmail(registrationData.getEmail())) {
            //todo: refactor to keeps exceptions in files
            throw new ValidationException("Email is already in use!");
        }
    }

    @Override
    public void setUserRole(Long userId, RoleEnum roleEnum) {
        if (roleEnum.equals(ROLE_ADMIN))
            validateCreatingAdminRole();


        User user = findUserById(userId);
        user.setRole(roleService.findRoleByName(roleEnum));

        userRepository.save(user);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("User not found"));
    }

    @Override
    public List<FullUserInfoDTO> findUsersByRole(RoleEnum role) {
        var users = userRepository.findAllByRoles(role.getName())
                .orElseThrow(() ->
                new IllegalArgumentException("User not found"));

        return users
                .stream()
                .map(userMapper::toView)
                .collect(Collectors.toList());
    }

    @Override
    public User getLoggedUser() {
        var loggedUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return findUserById(loggedUser.getId());
    }

    private void validateCreatingAdminRole(){
        if(!isLoggedUserAdmin())
            throw new AccessDeniedException("You are not allowed to create admins");
    }

    private boolean isLoggedUserAdmin(){
        var authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getAuthorities).orElse(null);

        if(authentication != null){
            return authentication.stream().map(GrantedAuthority::getAuthority).anyMatch(RoleEnum.ROLE_ADMIN::isEqual);
        }

        return false;
    }
}
