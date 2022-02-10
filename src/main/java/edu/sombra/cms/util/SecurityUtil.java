package edu.sombra.cms.util;

import edu.sombra.cms.config.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static UserDetailsImpl getLoggedUser(){
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
