package com.chess4you.lobbyserver.service;

import com.chess4you.lobbyserver.data.gamedata.GameServer;
import com.chess4you.lobbyserver.repository.IGameServerRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServerService {
    private Dictionary<String, GameServer> gameDictionary = new Hashtable<>();
    private IGameServerRepository gameServerRepository;

    @Autowired
    public GameServerService(IGameServerRepository gameRepository) {
        this.gameServerRepository = gameRepository;
        updateDictionary();
    }

    private void updateDictionary() {
        gameDictionary = new Hashtable<>();
        for(GameServer game : this.gameServerRepository.findAll()) {
            gameDictionary.put(game.getGameServerUuid(), game);
        }
    }

    public boolean isGameServerAvailable() {
        updateDictionary();
        Optional<GameServer> game = Collections.list(gameDictionary.elements())
                .stream()
                .filter(tmpGame -> !tmpGame.getIsRunning())
                .findFirst();
        return game.isPresent();
    }

    public GameServer getAvailableGameServer() {
        updateDictionary();
        Optional<GameServer> game = Collections.list(gameDictionary.elements())
                .stream()
                .filter(tmpGame -> tmpGame.getIsRunning() == false)
                .findFirst();
        return game.get();
    }
}
