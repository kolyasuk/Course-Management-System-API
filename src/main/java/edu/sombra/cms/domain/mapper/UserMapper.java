package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.config.security.UserDetailsImpl;
import edu.sombra.cms.domain.dto.FullUserInfoDTO;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.payload.UserRegistrationData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    @Lazy
    private final UserService userService;

    public FullUserInfoDTO toView(UserDetailsImpl userDetails){
        return new FullUserInfoDTO()
                .setId(userDetails.getId())
                .setFullName(userDetails.getFullName())
                .setEmail(userDetails.getEmail());
    }

    public FullUserInfoDTO toView(Long userId) throws SomethingWentWrongException {
        User user = userService.findUserById(userId);
        return new FullUserInfoDTO()
                .setId(user.getId())
                .setFullName(user.getFullName())
                .setEmail(user.getEmail());
    }

    public User fromRegistrationData(UserRegistrationData userRegistrationData) {
        return new User()
                .setFullName(userRegistrationData.getFullName())
                .setEmail(userRegistrationData.getEmail())
                .setRole(userRegistrationData.getRole())
                .setPassword(passwordEncoder.encode(userRegistrationData.getPassword()));
    }
}
