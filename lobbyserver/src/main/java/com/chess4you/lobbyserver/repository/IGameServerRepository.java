package com.chess4you.lobbyserver.repository;

import com.chess4you.lobbyserver.data.gamedata.GameServer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGameServerRepository extends MongoRepository<GameServer, String> {
}
