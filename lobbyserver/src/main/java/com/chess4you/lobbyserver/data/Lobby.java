package com.chess4you.lobbyserver.data;

import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
public class Lobby {
    @NonNull private UUID UUIDLobby;
    @NonNull private String LobbyName;
    @NonNull private Player PlayerOne;
    @NonNull private Color ColorPlayerOne;
    private Player PlayerTwo;
    private Color ColorPlayerTwo;
}
