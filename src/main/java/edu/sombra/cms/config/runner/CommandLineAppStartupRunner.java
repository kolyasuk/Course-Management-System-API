package edu.sombra.cms.config.runner;

import edu.sombra.cms.domain.enumeration.RoleEnum;
import edu.sombra.cms.domain.payload.RegistrationData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.repository.UserRepository;
import edu.sombra.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    @Value("${predefined.admin.password}")
    private String adminPassword;

    @Override
    public void run(String...args) throws SomethingWentWrongException {
        if(!userRepository.existsByUsername("admin")){
            RegistrationData adminRegistrationData = new RegistrationData()
                    .setUsername("admin")
                    .setPassword(adminPassword)
                    .setEmail("admin@gmail.com")
                    .setRole(RoleEnum.ROLE_ADMIN)
                    .setFullName("admin");

            userService.create(adminRegistrationData);
        }
    }
}
