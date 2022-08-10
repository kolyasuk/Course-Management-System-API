package edu.sombra.cms.controller.advisor;

import edu.sombra.cms.messages.SomethingWentWrongException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

import static edu.sombra.cms.messages.UserMessage.ACCESS_DENIED;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(ex), headers, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SomethingWentWrongException.class)
    public ResponseEntity<Object> handleSomethingWentWrong(SomethingWentWrongException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(ex), ex.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(ex), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(ACCESS_DENIED.ofException()), HttpStatus.NOT_FOUND);
    }

}