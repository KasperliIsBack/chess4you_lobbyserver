package com.chess4you.lobbyserver.repository;

import com.chess4you.lobbyserver.data.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlayerRepository extends MongoRepository<Player, String> {
}
