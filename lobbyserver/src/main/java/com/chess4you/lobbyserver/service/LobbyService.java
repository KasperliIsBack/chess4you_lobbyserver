package com.chess4you.lobbyserver.service;

import com.chess4you.lobbyserver.data.Color;
import com.chess4you.lobbyserver.data.Lobby;
import com.chess4you.lobbyserver.exceptionHandling.exception.*;
import com.squareup.okhttp.HttpUrl;
import lombok.var;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.*;

@Service
public class LobbyService {

    private GameService gameService;
    private PlayerService playerService;
    private Dictionary<UUID, Lobby> lobbyDictionary = new Hashtable<>();

    public LobbyService(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
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

    public URL initLobby(String lobbyName, String playerName, int chooseColor) throws Exception {
        if(gameService.isGameServerAvailable()) {
            if(!lobbyExists(lobbyName)) {
                var gameServer = gameService.getAvailableGameServer();
                var lobby = createLobby(lobbyName, playerName, chooseColor);
                URL url = new HttpUrl.Builder()
                        .scheme("http")
                        .host(gameServer.getHost())
                        .port(gameServer.getPort())
                        .query(lobby.getUUIDLobby().toString())
                        .build()
                        .url();
                return url;
            } else {
                throw new LobbyDoesNotExistsException(lobbyName);
            }
        } else {
            throw new NoServerAvailableException();
        }
    }

    public URL joinLobby(UUID lobbyUuid, String playerName) throws Exception {
        if(gameService.isGameServerAvailable()) {
            if(lobbyExists(lobbyUuid)) {
                if(lobbyIsNotFull(lobbyUuid)) {
                    if(playerNotAlreadyInLobby(lobbyUuid, playerName)) {
                        var gameServer = gameService.getAvailableGameServer();
                        var lobby = updateLobby(lobbyUuid, playerName);
                        URL url = new HttpUrl.Builder()
                                .scheme("http")
                                .host(gameServer.getHost())
                                .port(gameServer.getPort())
                                .query(lobby.getUUIDLobby().toString())
                                .build()
                                .url();
                        return url;
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
        lobbyDictionary.put(lobby.getUUIDLobby(), lobby);
        return lobby;
    }

    public Lobby updateLobby(UUID lobbyUuid, String playerName) {
        var player = playerService.createOrGetPlayer(playerName);
        var lobby = lobbyDictionary.get(lobbyUuid);
        lobby.setPlayerTwo(player);
        lobby.setColorPlayerTwo(Color.getOtherColor(lobby.getColorPlayerOne()));
        lobbyDictionary.put(lobby.getUUIDLobby(), lobby);
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
