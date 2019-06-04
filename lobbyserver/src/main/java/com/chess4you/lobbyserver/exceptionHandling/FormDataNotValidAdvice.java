package com.chess4you.lobbyserver.exceptionHandling;

import com.chess4you.lobbyserver.exceptionHandling.exception.FormDataNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FormDataNotValidAdvice {
    @ResponseBody
    @ExceptionHandler(FormDataNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String FormDataNotValidHandler(FormDataNotValidException ex){
        return ex.getMessage();
    }
}
