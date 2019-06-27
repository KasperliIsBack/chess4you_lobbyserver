package com.chess4you.lobbyserver.data.gamedata;

import com.chess4you.lobbyserver.data.Player;
import com.chess4you.lobbyserver.data.enums.Color;
import com.google.gson.Gson;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private String mapPosPiece;

    public void setMapPosPiece(Map<Position, Piece> mapPosPiece) {
        List<Piece> listPiece = new ArrayList<>();
        listPiece.addAll(mapPosPiece.values());
        List<Position> listPosition = new ArrayList<>();
        listPosition.addAll(mapPosPiece.keySet());

        for (int index = 0; index < listPiece.size(); index++) {
            var piece = listPiece.get(index);
            piece.setPosition(listPosition.get(index));
            listPiece.set(index, piece);
        }
        this.mapPosPiece = new Gson().toJson(listPiece);
    }
}