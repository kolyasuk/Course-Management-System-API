package edu.sombra.cms.util;

import edu.sombra.cms.config.security.UserDetailsImpl;
import edu.sombra.cms.messages.SomethingWentWrongException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static edu.sombra.cms.messages.UserMessage.ANONYMOUS_USER;

public class SecurityUtil {

    public static Optional<UserDetailsImpl> getLoggedUser(){
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetailsImpl){
            UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return Optional.of(principal);
        }
        return Optional.empty();
    }

    public static long getLoggedUserId() throws SomethingWentWrongException {
        return getLoggedUser().map(UserDetailsImpl::getId).orElseThrow(ANONYMOUS_USER::ofException);
    }

    public static String getLoggedUserIdString() {
        return getLoggedUser().map(UserDetailsImpl::getId).map(String::valueOf).orElse("anonymousUser");
    }
}
