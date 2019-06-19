package com.chess4you.lobbyserver.data.gamedata;

import com.chess4you.lobbyserver.data.Player;
import com.chess4you.lobbyserver.data.enums.Color;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Dictionary;

@Document(collection = "gameData")
@Data
@RequiredArgsConstructor
public class GameData {
    @Id private String gameUuid;
    @NonNull private String gameName;
    @NonNull private Player firstPlayer;
    @NonNull private Color colorFirstPlayer;
    private boolean isFirstPlayerConnected;
    @NonNull private Player secondPlayer;
    @NonNull private Color colorSecondPlayer;
    private boolean isSecondPlayerConnected;
    @NonNull private Player currentPlayer;
    private int gamePeriodInMinute;
    private Date turnDate;
    private Movement[] historyOfMovements;
    private Dictionary<Position, Piece> dicPosPiece;
}
