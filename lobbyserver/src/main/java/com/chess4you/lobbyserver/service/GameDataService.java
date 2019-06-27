package com.chess4you.lobbyserver.service;

import com.chess4you.lobbyserver.data.Lobby;
import com.chess4you.lobbyserver.data.Player;
import com.chess4you.lobbyserver.data.enums.Color;
import com.chess4you.lobbyserver.data.gamedata.GameData;
import com.chess4you.lobbyserver.repository.IGameDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.Optional;

@Service
public class GameDataService {
    private IGameDataRepository gameDataRepository;

    @Autowired
    public GameDataService(IGameDataRepository gameRepository) {
        this.gameDataRepository = gameRepository;
    }

    public GameData createGameData(Lobby lobby, boolean isPlayerTwo){
        GameData gameData = null;
        if(isPlayerTwo) {
            Optional<GameData> tmpGameData =  gameDataRepository.findById(lobby.getGameDataUuid());
            if(tmpGameData.isPresent()) {
                gameData = tmpGameData.get();
                gameData.setSecondPlayer(lobby.getPlayerTwo());
                gameData.setColorSecondPlayer(lobby.getColorPlayerTwo());
                gameData.setMapPosPiece(new Hashtable<>());
                gameDataRepository.save(gameData);
            }
        } else {
            GameData tmpGameData = new GameData(lobby.getLobbyName(), lobby.getPlayerOne(), lobby.getColorPlayerOne(), new Player("Not Connected"), Color.getOtherColor(lobby.getColorPlayerOne()), lobby.getPlayerOne());
            tmpGameData.setMapPosPiece(new Hashtable<>());
            gameData = gameDataRepository.save(tmpGameData);
            lobby.setGameDataUuid(gameData.getGameUuid());
        }
        return gameData;
    }
}
