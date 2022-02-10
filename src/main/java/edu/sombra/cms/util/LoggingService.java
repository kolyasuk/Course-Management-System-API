package edu.sombra.cms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        var loggedUser = SecurityUtil.getLoggedUser();

        return message + " by user: " + loggedUser.getUsername();
    }
}
