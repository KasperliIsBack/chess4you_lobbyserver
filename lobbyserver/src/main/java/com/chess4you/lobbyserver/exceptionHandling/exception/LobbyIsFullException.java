package com.chess4you.lobbyserver.exceptionHandling.exception;

import java.util.UUID;

public class LobbyIsFullException extends Exception {
    public LobbyIsFullException(UUID lobbyUuid){
        super(String.format("The Lobby: %s is not full!", lobbyUuid.toString()));
    }
}
