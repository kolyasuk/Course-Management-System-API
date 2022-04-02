package edu.sombra.cms.util;

import edu.sombra.cms.config.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LoggingService {

    private final Logger LOGGER;

    public LoggingService(Class<?> aClass) {
        this.LOGGER = LoggerFactory.getLogger(aClass);
    }

    public void info(String message, Object ... obj) {
        LOGGER.info(addUserInfo(message), obj);
    }

    public void error(String message, Throwable t) {
        LOGGER.error(addUserInfo(message), t);
    }

    private String addUserInfo(String message){
        var loggerUsername = Optional.ofNullable(SecurityUtil.getLoggedUser()).map(UserDetailsImpl::getUsername).orElse(null);

        return message + " by user: " + loggerUsername;
    }
}
