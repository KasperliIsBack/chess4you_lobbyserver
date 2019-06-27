package com.chess4you.lobbyserver.service;

import com.chess4you.lobbyserver.data.gamedata.GameServer;
import com.chess4you.lobbyserver.data.Player;
import lombok.var;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URL;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class LobbyServiceTests {
    @Mock private GameServerService gameService;
    @Mock private PlayerService playerService;
    private LobbyService lobbyService;

    @Before
    public void setMockOutput(){
        var gameServerOne = getTestGameServer();
        when(gameService.isGameServerAvailable()).thenReturn(true);
        when(gameService.getAvailableGameServer()).thenReturn(gameServerOne);
        when(playerService.createOrGetPlayer(anyString())).thenAnswer(i -> new Player(i.getArguments()[0].toString()));
    }

    @Before
    public void initService(){
        lobbyService = Mockito.spy(new LobbyService(gameService, playerService));
    }

    @Test
    @DisplayName("If it will return all existing Lobbies")
    public void TestGetAllLobbies(){
        lobbyService.createLobby("Test", "Andri", 1);
        Assert.assertThat(lobbyService.getAllLobbies().length, is(1));
    }

    @Test
    @DisplayName("If it will return a existing Lobby")
    public void TestGetLobby() throws Exception {
        var lobby = lobbyService.createLobby("Test", "Andri", 1);
        Assert.assertThat(lobbyService.getLobby(lobby.getLobbyUuid()), is(lobby));
    }

    @Test
    @DisplayName("If it will create a lobby if the lobby don't already exists")
    public void TestInitLobby() throws Exception {
        var url = lobbyService.initLobby("Test", "Andri", 1);

        verify(gameService, times(1)).isGameServerAvailable();
        verify(lobbyService, times(1)).lobbyExists(anyString());
        verify(gameService, times(1)).getAvailableGameServer();
        verify(lobbyService, times(1)).createLobby(anyString(), anyString(), anyInt());
        Assert.assertThat(url, is(instanceOf(URL.class)));
    }

    @Test
    @DisplayName("If it will create a lobby if the lobby don't already exists")
    public void TestJoinLobby() throws Exception {
        var tmpUrl =  lobbyService.initLobby("Test", "Andri", 1);
        var url = lobbyService.joinLobby(UUID.fromString(tmpUrl.getQuery()), "Fabio");
        verify(gameService, times(2)).isGameServerAvailable();
        verify(lobbyService, times(1)).lobbyExists(any(UUID.class));
        verify(lobbyService, times(1)).lobbyIsNotFull(any(UUID.class));
        verify(lobbyService, times(1)).playerNotAlreadyInLobby(any(UUID.class), anyString());
        verify(gameService, times(2)).getAvailableGameServer();
        verify(lobbyService, times(1)).updateLobby(any(UUID.class), anyString());
        Assert.assertThat(url, is(instanceOf(URL.class)));
    }

    private GameServer getTestGameServer() {
        var tmpGameServer = new GameServer("GameServerOne", "localhost", 8080);
        tmpGameServer.setGameServerUuid(UUID.randomUUID().toString());
        tmpGameServer.setIsRunning(false);
        return tmpGameServer;
    }
}
