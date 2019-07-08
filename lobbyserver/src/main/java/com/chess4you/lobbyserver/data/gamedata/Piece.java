package com.chess4you.lobbyserver.data.gamedata;

import com.chess4you.lobbyserver.data.enums.Color;
import com.chess4you.lobbyserver.data.enums.Direction;
import com.chess4you.lobbyserver.data.enums.PieceType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.UUID;

@Data
public class Piece {
    @Setter(AccessLevel.NONE) private UUID pieceUuid = UUID.randomUUID();
    @Setter(AccessLevel.NONE) private Color color;
    @Setter(AccessLevel.NONE) private String pieceName;
    private Position position;
    private PieceType pieceType;
    private Direction[] directions;
    public Piece(PieceType pieceType, Direction[] directions, Color color, Position position){
        pieceName = pieceType.name() + color.toString();
        this.pieceType = pieceType;
        this.directions = directions;
        this.color = color;
        this.position = position;
    }
}


