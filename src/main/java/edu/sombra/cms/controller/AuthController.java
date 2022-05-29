package edu.sombra.cms.controller;

import edu.sombra.cms.config.security.JwtTokenUtil;
import edu.sombra.cms.config.security.UserDetailsImpl;
import edu.sombra.cms.domain.dto.security.SignupResponseDTO;
import edu.sombra.cms.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static edu.sombra.cms.config.security.CustomAuthorizationFilter.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    private static final Logger LOGGER =  LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        final String refreshToken = request.getHeader(HttpHeaders.AUTHORIZATION).substring(TOKEN_PREFIX.length());

        if(jwtTokenUtil.containsRole(refreshToken)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername2(jwtTokenUtil.getUsername(refreshToken));
        return ResponseEntity.ok()
                .header("access_token", jwtTokenUtil.generateAccessToken(userDetails))
                .header("refresh_token", jwtTokenUtil.generateRefreshToken(userDetails))
                .body(SignupResponseDTO.ofUserDetails(userDetails));
    }

}
