package edu.sombra.cms.messages;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SomethingWentWrongException extends Exception {

    private final HttpStatus status;

    public SomethingWentWrongException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}