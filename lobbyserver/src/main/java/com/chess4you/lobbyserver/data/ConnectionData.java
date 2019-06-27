package com.chess4you.lobbyserver.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConnectionData {
   private String gameUuid;
   private String gameDataUuid;
   private String playerUuid;
}
