package edu.sombra.cms.config.runner;

import edu.sombra.cms.domain.dto.RegistrationDTO;
import edu.sombra.cms.domain.enumeration.RoleEnum;
import edu.sombra.cms.repository.UserRepository;
import edu.sombra.cms.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    @Value("${predefined.admin.password}")
    private String adminPassword;

    @Override
    public void run(String...args) {
        if(!userRepository.existsByUsername("admin")){
            RegistrationDTO admin = new RegistrationDTO()
                    .setUsername("admin")
                    .setPassword(adminPassword)
                    .setRePassword(adminPassword)
                    .setEmail("admin@gmail.com")
                    .setFullName("admin")
                    .setMessage("admin auto registration")
                    .setRoles(Set.of(RoleEnum.ROLE_ADMIN))
                    .setRequestedRole(RoleEnum.ROLE_ADMIN);

            userService.create(admin);
        }
    }
}
