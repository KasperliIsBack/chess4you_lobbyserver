package com.chess4you.lobbyserver.data.gamedata;

import lombok.Data;

@Data
public class LobbyDto {
    private String playerName;
    private String lobbyName;
    private int chooseColor;
}
