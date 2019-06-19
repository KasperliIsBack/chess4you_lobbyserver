package com.chess4you.lobbyserver.repository;

import com.chess4you.lobbyserver.data.gamedata.GameServer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IGameServerRepository extends MongoRepository<GameServer, String> {
}
