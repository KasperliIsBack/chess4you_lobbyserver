package com.chess4you.lobbyserver.exceptionHandling.exception;

public class FormDataNotValidException extends Exception {
    public FormDataNotValidException(String formData) {
        super(String.format("The FormData is not Valid! %s", formData));
    }
}
