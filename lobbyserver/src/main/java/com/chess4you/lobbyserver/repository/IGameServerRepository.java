package com.chess4you.lobbyserver.repository;

import com.chess4you.lobbyserver.data.GameServer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IGameServerRepository extends MongoRepository<GameServer, String> {
}
