package com.chess4you.lobbyserver.exceptionHandling.exception;

import java.util.Map;

public class FormDataNotValidException extends Exception {
    public FormDataNotValidException(Map<String, String> formData) {
        super(String.format("The FormData is not Valid! %s", formData.toString()));
    }
}
