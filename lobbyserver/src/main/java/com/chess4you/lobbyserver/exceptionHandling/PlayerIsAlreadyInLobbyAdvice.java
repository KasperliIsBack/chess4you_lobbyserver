package com.chess4you.lobbyserver.exceptionHandling;

import com.chess4you.lobbyserver.exceptionHandling.exception.PlayerIsAlreadyInLobbyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PlayerIsAlreadyInLobbyAdvice {
    @ResponseBody
    @ExceptionHandler(PlayerIsAlreadyInLobbyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String playerIsAlreadyInLobbyHandler(PlayerIsAlreadyInLobbyException ex) {
        return ex.getMessage();
    }
}
