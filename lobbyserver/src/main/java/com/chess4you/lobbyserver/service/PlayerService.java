package com.chess4you.lobbyserver.service;


import com.chess4you.lobbyserver.data.Player;
import com.chess4you.lobbyserver.repository.IPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PlayerService {

    private HashMap<String, Player> ListPlayer = new HashMap<>();
    private IPlayerRepository playerRepository;

    @Autowired
    public PlayerService(IPlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
        for(Player player : this.playerRepository.findAll()){
            ListPlayer.put(player.getPlayerName(), player);
        }
    }

    public Player createOrGetPlayer(String playerName) {
        Player player;
        if(playerExists(playerName)) {
            player = getPlayer(playerName);
        } else {
            player = setPlayer(new Player(playerName));
        }
        return player;
    }

    public boolean playerExists(String playerName){
        if(ListPlayer.containsKey(playerName)){
            return true;
        } else {
            return false;
        }
    }

    public Player setPlayer(Player player) {
        ListPlayer.put(player.getPlayerName(), player);
        return playerRepository.insert(player);
    }

    public Player getPlayer(String playerName) {
        return ListPlayer.get(playerName);
    }
}
