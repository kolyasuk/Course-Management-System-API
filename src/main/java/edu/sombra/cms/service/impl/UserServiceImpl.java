package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.FullUserInfoDTO;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.enumeration.RoleEnum;
import edu.sombra.cms.domain.mapper.UserMapper;
import edu.sombra.cms.domain.payload.RegistrationData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.repository.UserRepository;
import edu.sombra.cms.service.RoleService;
import edu.sombra.cms.service.UserService;
import edu.sombra.cms.util.LoggingService;
import edu.sombra.cms.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static edu.sombra.cms.domain.enumeration.RoleEnum.ROLE_ADMIN;
import static edu.sombra.cms.messages.UserMessage.*;


@Service
@Validated
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

    private static final LoggingService LOGGER = new LoggingService(UserServiceImpl.class);

    @Override
    public FullUserInfoDTO create(@Valid RegistrationData registrationData) throws SomethingWentWrongException {
        validateRegistrationData(registrationData);

        User userToRegister = userMapper.fromRegistrationData(registrationData);
        userRepository.save(userToRegister);
        var registeredUser = userMapper.toView(userToRegister);

        sendWelcomeEmail(registeredUser.getEmail());

        LOGGER.info("Created user {} with id: {}", registeredUser.getUsername(), registeredUser.getId());
        return registeredUser;
    }

    private void sendWelcomeEmail(String email){
        System.out.println("Send message on mail to complete the registration");
    }

    private void validateRegistrationData(@Valid RegistrationData registrationData) throws SomethingWentWrongException {
        if (userRepository.existsByUsername(registrationData.getUsername())) {
            throw USERNAME_EXISTS.ofException();
        }
        if (userRepository.existsByEmail(registrationData.getEmail())) {
            throw EMAIL_EXISTS.ofException();
        }
    }

    @Override
    public void setUserRole(Long userId, RoleEnum roleEnum) throws SomethingWentWrongException {
        if (roleEnum.equals(ROLE_ADMIN))
            validateCreatingAdminRole();


        User user = findUserById(userId);
        user.setRole(roleService.findRoleByName(roleEnum));

        userRepository.save(user);

        LOGGER.info("Set role {} to user with id: {}", roleEnum.getName(), user.getId());
    }

    @Override
    public User findUserById(Long userId) throws SomethingWentWrongException {
        return userRepository.findById(userId).orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    public List<FullUserInfoDTO> findUsersByRole(RoleEnum role) throws SomethingWentWrongException {
        var users = userRepository.findAllByRoles(role.getName()).orElseThrow(NOT_FOUND::ofException);

        return users
                .stream()
                .map(userMapper::toView)
                .collect(Collectors.toList());
    }

    @Override
    public User getLoggedUser() throws SomethingWentWrongException {
        var loggedUser = SecurityUtil.getLoggedUser();

        return findUserById(loggedUser.getId());
    }

    @Override
    public void loggedUserHasAccess(List<User> usersWithAccess) throws SomethingWentWrongException {
        var loggedUser = getLoggedUser();

        if(!usersWithAccess.contains(loggedUser) && !loggedUser.isAdmin()){
            throw ACCESS_DENIED.ofException();
        }
    }

    private void validateCreatingAdminRole() throws SomethingWentWrongException {
        if(!isLoggedUserAdmin())
            throw CANNOT_CREATE_ADMIN.ofException();
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
