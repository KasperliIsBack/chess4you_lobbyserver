package com.chess4you.lobbyserver.service;

import com.chess4you.lobbyserver.data.Player;
import com.chess4you.lobbyserver.repository.IPlayerRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTests {
    @Mock private IPlayerRepository playerRepository;
    private PlayerService playerService;

    @Before
    public void setMockOutput(){
        var tmpList = getListPlayers();
        when(playerRepository.findAll()).thenReturn(tmpList);
        when(playerRepository.insert(any(Player.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Before
    public void initService(){
        playerService = Mockito.spy(new PlayerService(playerRepository));
    }

    @Test
    @DisplayName("If a Player exists then it will return true")
    public void TestPlayerExists(){
        Assert.assertThat(playerService.playerExists("Andri"), is(true));
    }

    @Test
    @DisplayName("When a Player doesn't exists then it will return false")
    public void TestPlayerDoesNotExists(){
        Assert.assertThat(playerService.playerExists("Leandro"), is(false));
    }

    @Test
    @DisplayName("When a Player doesn't exists then it will return false")
    public void TestGetPlayer(){
        Assert.assertThat(playerService.getPlayer("Andri").getPlayerName(), is("Andri"));
    }

    @Test
    @DisplayName("When a Player doesn't exists then it will return false")
    public void TestSetPlayer(){
        playerService.setPlayer(new Player("Andri"));
        verify(playerRepository, times(1)).insert(any(Player.class));
    }

    @Test
    @DisplayName("If it will return the existing Player")
    public void TestCreateButDoesNotGetPlayer(){
        playerService.createOrGetPlayer("Andri");
        verify(playerRepository, times(0)).insert(new Player("Andri"));
        verify(playerService, times(1)).playerExists("Andri");
        verify(playerService, times(1)).getPlayer("Andri");
    }

    @Test
    @DisplayName("If it will create a new Player")
    public void TestDoesNotCreateButGetPlayer(){
        playerService.createOrGetPlayer("Leandro");
        verify(playerRepository, times(1)).insert(new Player("Leandro"));
        verify(playerService, times(1)).playerExists("Leandro");
        verify(playerService, times(0)).getPlayer("Leandro");
    }

    private List<Player> getListPlayers() {
        var tmpList = new ArrayList<Player>();
        var playerOne = new Player("Andri");
        var playerTwo = new Player("Fabio");
        tmpList.add(playerOne);
        tmpList.add(playerTwo);
        return tmpList;
    }
}
