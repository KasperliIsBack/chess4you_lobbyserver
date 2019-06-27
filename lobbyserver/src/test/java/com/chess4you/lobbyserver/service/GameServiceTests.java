package com.chess4you.lobbyserver.service;

import com.chess4you.lobbyserver.data.gamedata.GameServer;
import com.chess4you.lobbyserver.repository.IGameServerRepository;
import lombok.var;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class GameServiceTests {
    @Mock private IGameServerRepository gameRepositoryFull;
    @Mock private IGameServerRepository gameRepositoryEmpty;

    private GameServerService gameServiceFull;
    private GameServerService gameServiceEmpty;

    @Before
    public void setMockOutput(){
        var tmpListFull = getListGameServerFull();
        var tmpListEmpty = getListGameServerEmpty();
        when(gameRepositoryFull.findAll()).thenReturn(tmpListFull);
        when(gameRepositoryEmpty.findAll()).thenReturn(tmpListEmpty);
    }

    @Before
    public void initService(){
        gameServiceFull = new GameServerService(gameRepositoryFull);
        gameServiceEmpty = new GameServerService(gameRepositoryEmpty);
    }

    @Test
    @DisplayName("If any GameServer is available it will return true")
    public void TestIsGameServerAvailable(){
        Assert.assertThat(gameServiceFull.isGameServerAvailable(), is(true));
    }

    @Test
    @DisplayName("When no GameServer is available it will return false")
    public void TestIsGameServerNotAvailable(){
        Assert.assertThat(gameServiceEmpty.isGameServerAvailable(), is(false));
    }

    @Test
    @DisplayName("When the GameServer Object returned, the Server is not running")
    public void TestGetAvailableGameServerAvailable(){
        Assert.assertThat(gameServiceFull.getAvailableGameServer().getIsRunning(), is(false));
    }

    private List<GameServer> getListGameServerFull() {
        var tmpList = new ArrayList<GameServer>();
        var gameServerOne = new GameServer("GameServerOne", "localhost", 8080);
        gameServerOne.setGameServerUuid(UUID.randomUUID().toString());
        gameServerOne.setIsRunning(false);
        tmpList.add(gameServerOne);
        return tmpList;
    }


    private List<GameServer> getListGameServerEmpty() {
        var tmpList = new ArrayList<GameServer>();
        var gameServerOne = new GameServer("GameServerOne", "localhost", 8080);
        gameServerOne.setGameServerUuid(UUID.randomUUID().toString());
        gameServerOne.setIsRunning(true);
        tmpList.add(gameServerOne);
        return tmpList;
    }
}
