package edu.sombra.cms.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StudentLessonMessage implements ThrowMessage {

    NOT_FOUND("Student lesson not found", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;

}
