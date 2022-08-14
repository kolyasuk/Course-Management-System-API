package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.FullUserInfoDTO;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.enumeration.Role;
import edu.sombra.cms.domain.mapper.UserMapper;
import edu.sombra.cms.domain.payload.UserRegistrationData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.repository.UserRepository;
import edu.sombra.cms.service.UserService;
import edu.sombra.cms.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static edu.sombra.cms.domain.enumeration.Role.ROLE_ADMIN;
import static edu.sombra.cms.messages.UserMessage.*;


@Service
@Validated
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public FullUserInfoDTO create(@Valid UserRegistrationData userRegistrationData) throws SomethingWentWrongException {
        validateRegistrationData(userRegistrationData);

        User userToRegister = userMapper.fromRegistrationData(userRegistrationData);
        userRepository.save(userToRegister);
        var registeredUser = userMapper.toView(userToRegister.getId());

        LOGGER.info("Created user {} with id: {}", registeredUser.getEmail(), registeredUser.getId());
        return registeredUser;
    }

    private void validateRegistrationData(@Valid UserRegistrationData userRegistrationData) throws SomethingWentWrongException {
        if (userRepository.existsByEmail(userRegistrationData.getEmail())) {
            throw EMAIL_EXISTS.ofException();
        }
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public void setUserRole(Long userId, Role role) throws SomethingWentWrongException {
        if (role.equals(ROLE_ADMIN))
            validateCreatingAdminRole();


        User user = findUserById(userId);
        user.setRole(role);

        userRepository.save(user);

        LOGGER.info("Set role {} to user with id: {}", role.getRoleName(), user.getId());
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public User findUserById(Long userId) throws SomethingWentWrongException {
        return userRepository.findById(userId).orElseThrow(NOT_FOUND::ofException);
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public List<FullUserInfoDTO> findUsersByRole(Role role) throws SomethingWentWrongException {
        var users = userRepository.findAllByRole(role);

        List<FullUserInfoDTO> res = new ArrayList<>();
        for (User user : users) {
            res.add(userMapper.toView(user.getId()));
        }

        return res;
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public User getLoggedUser() throws SomethingWentWrongException {
        var loggedUserId = SecurityUtil.getLoggedUserId();

        return findUserById(loggedUserId);
    }

    private void validateCreatingAdminRole() throws SomethingWentWrongException {
        if(!isLoggedUserAdmin())
            throw CANNOT_CREATE_ADMIN.ofException();
    }

    private boolean isLoggedUserAdmin(){
        var authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getAuthorities).orElse(null);

        if(authentication != null){
            return authentication.stream().map(GrantedAuthority::getAuthority).anyMatch(ROLE_ADMIN.name()::equals);
        }

        return false;
    }
}
