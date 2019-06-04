package com.chess4you.lobbyserver.exceptionHandling;

import com.chess4you.lobbyserver.exceptionHandling.exception.NoServerAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NoServerAvailableAdvice {
    @ResponseBody
    @ExceptionHandler(NoServerAvailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    String serviceNotAvailableHandler(NoServerAvailableException ex) {
        return ex.getMessage();
    }
}
