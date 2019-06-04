package com.chess4you.lobbyserver.exceptionHandling.exception;

import java.util.UUID;

public class PlayerIsAlreadyInLobbyException extends Exception {
    public PlayerIsAlreadyInLobbyException(UUID lobbyUuid, String playerName) {
        super(String.format("The Player: %s is already in the Lobby: %s!", playerName, lobbyUuid.toString()));
    }
}
