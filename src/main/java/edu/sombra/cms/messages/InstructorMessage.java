package edu.sombra.cms.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum InstructorMessage implements ThrowMessage {

    NOT_FOUND("Instructor not found", HttpStatus.NOT_FOUND),
    INFO_ALREADY_CREATED("Instructor info is already created", HttpStatus.BAD_REQUEST),
    USER_IS_NOT_INSTRUCTOR("You are not instructor", HttpStatus.BAD_REQUEST),
    EMPTY_USER_ID("Unable to create instructor, user id is null", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

}
