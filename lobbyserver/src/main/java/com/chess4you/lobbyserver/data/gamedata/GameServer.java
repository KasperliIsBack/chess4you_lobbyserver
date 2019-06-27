package com.chess4you.lobbyserver.data.gamedata;

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
    private String gameServerUuid;
    @NonNull private String Name;
    @NonNull private String Host;
    @NonNull private int Port;
    @NonNull private Boolean IsRunning;
}

