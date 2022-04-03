package edu.sombra.cms.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LessonMessage implements ThrowMessage {

    NOT_FOUND("Lesson not found", HttpStatus.NOT_FOUND),
    NOT_COURSE_INSTRUCTOR("Not the course instructor", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;

}
