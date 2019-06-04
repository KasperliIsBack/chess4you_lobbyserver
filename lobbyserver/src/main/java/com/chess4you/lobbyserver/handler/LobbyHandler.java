package com.chess4you.lobbyserver.handler;

import com.chess4you.lobbyserver.service.LobbyService;
import com.google.gson.Gson;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        var lobbies = lobbyService.getAllLobbies();
        return gson.toJson(lobbies);
    }

    public String getLobby(UUID lobbyUuid) throws Exception {
        var lobby = lobbyService.getLobby(lobbyUuid);
        return gson.toJson(lobby);
    }

    public String initLobby(String lobbyName, String playerName, int chooseColor) throws Exception {
        var url = lobbyService.initLobby(lobbyName, playerName, chooseColor);
        return url.toString();
    }

    public String joinLobby(UUID lobbyUuid, String playerName) throws Exception {
        var url = lobbyService.joinLobby(lobbyUuid, playerName);
        return url.toString();
    }
}
