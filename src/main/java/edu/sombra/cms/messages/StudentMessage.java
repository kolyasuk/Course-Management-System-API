package edu.sombra.cms.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StudentMessage implements ThrowMessage {

    NOT_FOUND("Student not found", HttpStatus.NOT_FOUND),
    USER_NOT_STUDENT("You are not a student", HttpStatus.BAD_REQUEST),
    INFO_ALREADY_CREATED("Student info is already created", HttpStatus.BAD_REQUEST),
    EMPTY_USER_ID("Unable to create student, user id is null", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

}
