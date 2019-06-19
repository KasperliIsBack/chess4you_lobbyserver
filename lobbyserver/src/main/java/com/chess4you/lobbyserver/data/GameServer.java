package com.chess4you.lobbyserver.data;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "gameServers")
@Data
@RequiredArgsConstructor
public class GameServer {
    @Id
    private String UUIDGameServer;
    final private String Name;
    final private String Host;
    final private int Port;
    private Boolean IsRunning;
}
