package com.chess4you.lobbyserver.exceptionHandling.exception;

public class LobbyDoesNotExistsException extends Exception {
    public LobbyDoesNotExistsException(String lobbyUuid) {
        super(String.format("The Lobby: %s does not exists!", lobbyUuid));
    }
}
