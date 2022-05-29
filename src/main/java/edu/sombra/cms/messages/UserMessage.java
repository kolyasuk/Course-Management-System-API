package edu.sombra.cms.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserMessage implements ThrowMessage {

    NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    ANONYMOUS_USER("User is not logged", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS("Email already exists", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED("You can't get such info", HttpStatus.FORBIDDEN),
    CANNOT_CREATE_ADMIN("You are not allowed to create admins", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

}
