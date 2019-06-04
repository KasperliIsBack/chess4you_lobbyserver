package com.chess4you.lobbyserver.service;

import com.chess4you.lobbyserver.data.GameServer;
import com.chess4you.lobbyserver.repository.IGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    private Dictionary<UUID, GameServer> gameDictionary = new Hashtable<>();
    private IGameRepository gameRepository;

    @Autowired
    public GameService(IGameRepository gameRepository) {
        this.gameRepository = gameRepository;
        for(GameServer game : this.gameRepository.findAll()) {
            gameDictionary.put(UUID.fromString(game.getUUIDGameServer()), game);
        }
    }

    public boolean isGameServerAvailable() {
        Optional<GameServer> game = Collections.list(gameDictionary.elements())
                .stream()
                .filter(tmpGame -> tmpGame.getIsRunning() == false)
                .findFirst();
        return game.isPresent();
    }

    public GameServer getAvailableGameServer() {
        Optional<GameServer> game = Collections.list(gameDictionary.elements())
                .stream()
                .filter(tmpGame -> tmpGame.getIsRunning() == false)
                .findFirst();
        return game.get();
    }
}
