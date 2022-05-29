package edu.sombra.cms.domain.dto.security;

import edu.sombra.cms.config.security.UserDetailsImpl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class SignupResponseDTO {

    private Long id;
    private String email;
    private String role;

    public static SignupResponseDTO ofUserDetails(UserDetailsImpl userDetails){
        SignupResponseDTO signupResponseDTO = new SignupResponseDTO();

        signupResponseDTO.setId(userDetails.getId());
        signupResponseDTO.setEmail(userDetails.getEmail());
        signupResponseDTO.setRole(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).findAny().get());

        return signupResponseDTO;
    }

    public static SignupResponseDTO ofUserDTO(UserDetailsImpl userDetails){
        SignupResponseDTO signupResponseDTO = new SignupResponseDTO();

        signupResponseDTO.setId(userDetails.getId());
        signupResponseDTO.setEmail(userDetails.getEmail());
        signupResponseDTO.setRole(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).findAny().get());

        return signupResponseDTO;
    }

}
