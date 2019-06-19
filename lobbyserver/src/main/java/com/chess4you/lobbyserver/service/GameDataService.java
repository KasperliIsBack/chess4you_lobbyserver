package com.chess4you.lobbyserver.service;

import com.chess4you.lobbyserver.data.Lobby;
import com.chess4you.lobbyserver.data.Player;
import com.chess4you.lobbyserver.data.enums.Color;
import com.chess4you.lobbyserver.data.gamedata.GameData;
import com.chess4you.lobbyserver.repository.IGameDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameDataService {
    private IGameDataRepository gameDataRepository;

    @Autowired
    public GameDataService(IGameDataRepository gameRepository) {
        this.gameDataRepository = gameRepository;
    }

    public void createGameData(Lobby lobby, boolean isPlayerTwo){
        if(isPlayerTwo) {
            GameData gameData = new GameData(lobby.getLobbyName(), lobby.getPlayerOne(), lobby.getColorPlayerOne(), new Player("a"), Color.Black, lobby.getPlayerOne());
            gameDataRepository.insert(gameData);
        } else {
            GameData gameData = new GameData(lobby.getLobbyName(), lobby.getPlayerOne(), lobby.getColorPlayerOne(), lobby.getPlayerTwo(), lobby.getColorPlayerTwo(), lobby.getPlayerOne());
            gameDataRepository.save(gameData);
        }
    }
}
