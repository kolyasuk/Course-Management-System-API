package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.config.security.UserDetailsImpl;
import edu.sombra.cms.domain.dto.RegistrationDTO;
import edu.sombra.cms.domain.dto.UserDTO;
import edu.sombra.cms.domain.entity.Role;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.payload.UserView;
import edu.sombra.cms.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserDTO toDTO(User user){
        return new UserDTO()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setFullName(user.getFullName());
    }

    public UserView toView(UserDetailsImpl userDetails){
        return new UserView()
                .setId(userDetails.getId())
                .setUsername(userDetails.getUsername())
                .setFullName(userDetails.getFullName())
                .setEmail(userDetails.getEmail());
    }

    public UserView toView(User user){
        return new UserView()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setFullName(user.getFullName())
                .setEmail(user.getEmail())
                .setMessage(user.getMessage())
                .setRequestedRole(user.getRequestedRole());
    }

    public User fromDTO(UserDTO userDTO){
        return new User()
                .setId(userDTO.getId())
                .setUsername(userDTO.getUsername())
                .setFullName(userDTO.getFullName())
                .setPassword(passwordEncoder.encode(userDTO.getPassword()));
    }

    public User fromRegistrationDTO(RegistrationDTO registrationDTO){
        return new User()
                .setUsername(registrationDTO.getUsername())
                .setFullName(registrationDTO.getFullName())
                .setEmail(registrationDTO.getEmail())
                .setMessage(registrationDTO.getMessage())
                .setRequestedRole(registrationDTO.getRequestedRole())
                .setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
    }
}
