package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.config.security.UserDetailsImpl;
import edu.sombra.cms.domain.dto.FullUserInfoDTO;
import edu.sombra.cms.domain.dto.UserDTO;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.payload.RegistrationData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserDTO toDTO(User user){
        return new UserDTO()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setFullName(user.getFullName());
    }

    public FullUserInfoDTO toView(UserDetailsImpl userDetails){
        return new FullUserInfoDTO()
                .setId(userDetails.getId())
                .setUsername(userDetails.getUsername())
                .setFullName(userDetails.getFullName())
                .setEmail(userDetails.getEmail());
    }

    public FullUserInfoDTO toView(User user){
        return new FullUserInfoDTO()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setFullName(user.getFullName())
                .setEmail(user.getEmail());
    }

    public User fromDTO(UserDTO userDTO){
        return new User()
                .setId(userDTO.getId())
                .setUsername(userDTO.getUsername())
                .setFullName(userDTO.getFullName())
                .setPassword(passwordEncoder.encode(userDTO.getPassword()));
    }

    public User fromRegistrationData(RegistrationData registrationData) throws SomethingWentWrongException {
        return new User()
                .setUsername(registrationData.getUsername())
                .setFullName(registrationData.getFullName())
                .setEmail(registrationData.getEmail())
                .setRole(roleService.findRoleByName(registrationData.getRole()))
                .setPassword(passwordEncoder.encode(registrationData.getPassword()));
    }
}
