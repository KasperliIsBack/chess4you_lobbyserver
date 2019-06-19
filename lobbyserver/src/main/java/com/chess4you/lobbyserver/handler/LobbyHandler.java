package com.chess4you.lobbyserver.handler;

import com.chess4you.lobbyserver.data.ConnectionData;
import com.chess4you.lobbyserver.data.Lobby;
import com.chess4you.lobbyserver.service.LobbyService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.UUID;

@Component
public class LobbyHandler {

    private LobbyService lobbyService;
    private Gson gson;

    @Autowired
    public LobbyHandler(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
        gson = new Gson();
    }

    public String getLobbies() {
        Lobby[] lobbies = lobbyService.getAllLobbies();
        return gson.toJson(lobbies);
    }

    public String getLobby(UUID lobbyUuid) throws Exception {
        Lobby lobby = lobbyService.getLobby(lobbyUuid);
        return gson.toJson(lobby);
    }

    public String initLobby(String lobbyName, String playerName, int chooseColor) throws Exception {
        ConnectionData urlGameServer = lobbyService.initLobby(lobbyName, playerName, chooseColor);
        return urlGameServer.toString();
    }

    public String joinLobby(UUID lobbyUuid, String playerName) throws Exception {
        ConnectionData urlGameServer = lobbyService.joinLobby(lobbyUuid, playerName);
        return urlGameServer.toString();
    }
}
