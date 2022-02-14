package edu.sombra.cms.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StudentLessonMessage implements ThrowMessage {

    NOT_FOUND("Student lesson not found", HttpStatus.NOT_FOUND),
    UNABLE_TO_UPLOAD_HOMEWORK("Unable to upload homework", HttpStatus.NOT_FOUND),
    HOMEWORK_IS_NOT_UPLOADED("Homework is not uploaded yet", HttpStatus.NOT_FOUND),
    NO_FILE_FOUND_WITH_SUCH_KEY("Not file found with such key", HttpStatus.NOT_FOUND),
    HOMEWORK_IS_ALREADY_UPLOADED("Homework is already uploaded", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;

}
