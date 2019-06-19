package com.chess4you.lobbyserver.data;

import com.chess4you.lobbyserver.data.enums.Color;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
public class Lobby {
    @NonNull private UUID lobbyUuid;
    @NonNull private String lobbyName;
    @NonNull private Player playerOne;
    @NonNull private Color colorPlayerOne;
    private Player playerTwo;
    private Color colorPlayerTwo;
}
