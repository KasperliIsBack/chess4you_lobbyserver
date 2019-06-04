package com.chess4you.lobbyserver.exceptionHandling;

import com.chess4you.lobbyserver.exceptionHandling.exception.LobbyDoesNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LobbyDoesNotExistsAdvice {
    @ResponseBody
    @ExceptionHandler(LobbyDoesNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String lobbyDoesNotExistsHandler(LobbyDoesNotExistsException ex) {
        return ex.getMessage();
    }
}
