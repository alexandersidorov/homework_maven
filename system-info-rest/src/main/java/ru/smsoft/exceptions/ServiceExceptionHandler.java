package ru.smsoft.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.smsoft.responses.ErrorResponse;

@ControllerAdvice
public class ServiceExceptionHandler {

    private static final String DEFAULT_ERROR_DEBUG_MESSAGE = "Unclassified internal server error.";

    @ExceptionHandler({CustomException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),ex.getResponseStatus());
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex) {
        return new ErrorResponse(DEFAULT_ERROR_DEBUG_MESSAGE);
    }
}

