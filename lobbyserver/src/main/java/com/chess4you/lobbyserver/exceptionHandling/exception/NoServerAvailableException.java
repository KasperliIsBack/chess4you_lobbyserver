package com.chess4you.lobbyserver.exceptionHandling.exception;

public class NoServerAvailableException extends Exception {
    public NoServerAvailableException() {
        super("At the moment no servers are available!");
    }
}
