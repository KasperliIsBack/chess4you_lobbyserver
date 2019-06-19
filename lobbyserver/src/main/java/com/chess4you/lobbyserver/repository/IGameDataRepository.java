package com.chess4you.lobbyserver.repository;

import com.chess4you.lobbyserver.data.gamedata.GameData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IGameDataRepository extends MongoRepository<GameData, String> {
}
