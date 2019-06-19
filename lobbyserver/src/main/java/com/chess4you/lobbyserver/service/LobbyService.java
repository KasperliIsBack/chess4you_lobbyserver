package com.chess4you.lobbyserver.service;

import com.chess4you.lobbyserver.data.ConnectionData;
import com.chess4you.lobbyserver.data.enums.Color;
import com.chess4you.lobbyserver.data.Lobby;
import com.chess4you.lobbyserver.exceptionHandling.exception.*;
import com.squareup.okhttp.HttpUrl;
import lombok.var;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.*;

@Service
public class LobbyService {

    private GameServerService gameServerService;
    private GameDataService gameDataService;
    private PlayerService playerService;
    private Dictionary<UUID, Lobby> lobbyDictionary = new Hashtable<>();

    public LobbyService(GameServerService gameService, PlayerService playerService, GameDataService gameDataService) {
        this.gameServerService = gameService;
        this.playerService = playerService;
        this.gameDataService = gameDataService;
    }

    public Lobby[] getAllLobbies() {
        var lobbies =  Collections.list(lobbyDictionary.elements());
        return lobbies.stream().toArray(element -> new Lobby[element]);
    }

    public Lobby getLobby(UUID lobbyUuid) throws Exception {
        var lobby = lobbyDictionary.get(lobbyUuid);
        if(lobby != null) {
            return lobby;
        } else {
            throw new LobbyDoesNotExistsException(lobbyUuid.toString());
        }
    }

    public ConnectionData initLobby(String lobbyName, String playerName, int chooseColor) throws Exception {
        if(gameServerService.isGameServerAvailable()) {
            if(!lobbyExists(lobbyName)) {
                var gameServer = gameServerService.getAvailableGameServer();
                var lobby = createLobby(lobbyName, playerName, chooseColor);
                return new ConnectionData(lobby.getLobbyUuid().toString(), lobby.getPlayerOne().getPlayerUUID().toString());
            } else {
                throw new LobbyDoesNotExistsException(lobbyName);
            }
        } else {
            throw new NoServerAvailableException();
        }
    }

    public ConnectionData joinLobby(UUID lobbyUuid, String playerName) throws Exception {
        if(gameServerService.isGameServerAvailable()) {
            if(lobbyExists(lobbyUuid)) {
                if(lobbyIsNotFull(lobbyUuid)) {
                    if(playerNotAlreadyInLobby(lobbyUuid, playerName)) {
                        var gameServer = gameServerService.getAvailableGameServer();
                        var lobby = updateLobby(lobbyUuid, playerName);
                        return new ConnectionData(lobby.getLobbyUuid().toString(), lobby.getPlayerTwo().getPlayerUUID().toString());
                    } else {
                        throw new PlayerIsAlreadyInLobbyException(lobbyUuid, playerName);
                    }
                } else {
                    throw new LobbyIsFullException(lobbyUuid);
                }
            }else {
                throw new LobbyDoesNotExistsException(lobbyUuid.toString());
            }
        } else {
            throw new NoServerAvailableException();
        }
    }

    public Lobby createLobby(String lobbyName, String playerName, int chooseColor){
        var playerOne = playerService.createOrGetPlayer(playerName);
        var lobby = new Lobby(UUID.randomUUID(), lobbyName,
                playerOne, Color.getColorById(chooseColor));
        lobbyDictionary.put(lobby.getLobbyUuid(), lobby);
        return lobby;
    }

    public Lobby updateLobby(UUID lobbyUuid, String playerName) {
        var player = playerService.createOrGetPlayer(playerName);
        var lobby = lobbyDictionary.get(lobbyUuid);
        lobby.setPlayerTwo(player);
        lobby.setColorPlayerTwo(Color.getOtherColor(lobby.getColorPlayerOne()));
        lobbyDictionary.put(lobby.getLobbyUuid(), lobby);
        gameDataService.createGameData(lobby, true);
        return lobby;
    }

    public boolean lobbyExists(UUID lobbyUuid) {
        var lobbiesUuid = Collections.list(lobbyDictionary.keys());
        return lobbiesUuid.contains(lobbyUuid) ? true : false;
    }

    public boolean lobbyExists(String lobbyName) {
        Optional<Lobby> tmpLobby = Collections.list(lobbyDictionary.elements())
                .stream()
                .filter(lobby -> lobby.getLobbyName() == lobbyName)
                .findFirst();
        return tmpLobby.isPresent();
    }

    public boolean playerNotAlreadyInLobby(UUID lobbyUuid, String playerName) {
        var lobby = lobbyDictionary.get(lobbyUuid);
        return lobby.getPlayerOne().getPlayerName() != playerName ? true : false;
    }

    public boolean lobbyIsNotFull(UUID lobbyUuid) {
        var lobby = lobbyDictionary.get(lobbyUuid);
        return lobby.getPlayerTwo() == null ? true : false;
    }
}
