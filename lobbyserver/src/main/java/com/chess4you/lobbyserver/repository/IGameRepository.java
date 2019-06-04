package com.chess4you.lobbyserver.repository;

import com.chess4you.lobbyserver.data.GameServer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IGameRepository extends MongoRepository<GameServer, String> {
}
