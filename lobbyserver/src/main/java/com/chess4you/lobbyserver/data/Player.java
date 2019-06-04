package com.chess4you.lobbyserver.data;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;

@Document(collection = "players")
@Data
@RequiredArgsConstructor
public class Player {
    @Id
    private String PlayerUUID;
    @NonNull @Size(max = 12) private String playerName;
}
