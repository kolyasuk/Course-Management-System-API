package edu.sombra.cms.messages;


import org.springframework.http.HttpStatus;

public interface ThrowMessage {

    String getMessage();
    HttpStatus getStatus();

    default SomethingWentWrongException ofException(){
        return new SomethingWentWrongException(getMessage(), getStatus());
    }

}
