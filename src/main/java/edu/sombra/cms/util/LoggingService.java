package edu.sombra.cms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingService {

    private final Class<?> aClass;

    public LoggingService(Class<?> aClass) {
        this.aClass = aClass;
    }

    public void info(String message, Object ... obj) {
        Logger LOGGER = LoggerFactory.getLogger(aClass);

        LOGGER.info(addUserInfo(message), obj);
    }

    public void error(String message, Throwable t) {
        Logger LOGGER = LoggerFactory.getLogger(aClass);

        LOGGER.error(addUserInfo(message), t);
    }

    private String addUserInfo(String message){
        var loggedUser = SecurityUtil.getLoggedUser();

        return message + " by user " + loggedUser.getUsername();
    }
}
