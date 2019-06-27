package com.chess4you.lobbyserver.controller;

import com.chess4you.lobbyserver.data.gamedata.LobbyDto;
import com.chess4you.lobbyserver.data.gamedata.LobbySmallDto;
import com.chess4you.lobbyserver.exceptionHandling.exception.FormDataNotValidException;
import com.chess4you.lobbyserver.exceptionHandling.exception.InvalidJsonObjectException;
import com.chess4you.lobbyserver.handler.LobbyHandler;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Slf4j
@RestController
@Api("Controller for creating or retrieving Lobbies")
public class LobbyController {

    private final String origin = "http://localhost:4200";
    private LobbyHandler lobbyHandler;
    private Gson gson;

    @Autowired
    LobbyController(LobbyHandler lobbyHandler) {
        this.lobbyHandler = lobbyHandler;
        this.gson = new Gson();
    }

    @CrossOrigin(origins = origin)
    @GetMapping("/getListLobbies")
    @ApiOperation("Return a List of all Lobbies")
    String getListLobby(){
        return lobbyHandler.getLobbies();
    }

    @CrossOrigin(origins = origin)
    @GetMapping("/getLobby")
    @ResponseBody
    @ApiOperation("Return a specific lobby by their uuid.")
    String getLobby(@ApiParam("The uuid of the lobby Cannot be null!") @RequestParam("gameUuid") String lobbyUuid) throws Exception {
        return lobbyHandler.getLobby(UUID.fromString(lobbyUuid));
    }

    @CrossOrigin(origins = origin)
    @PostMapping(value = "/initLobby", consumes = {"application/json"})
    @ApiOperation("Initialize a lobby")
    @ResponseBody
    String initLobby(@ApiParam("The serialized DataInit Object. Cannot be null!") @RequestBody String rawLobbyDto) throws Exception {
        LobbyDto lobbyDto;
        try {
            lobbyDto = gson.fromJson(rawLobbyDto, LobbyDto.class);
        } catch (Exception ex) {
            throw new InvalidJsonObjectException(rawLobbyDto);
        }
        if(isLobbyDtoValid(lobbyDto)) {
            return lobbyHandler.initLobby(lobbyDto.getLobbyName(), lobbyDto.getPlayerName(), lobbyDto.getChooseColor());
        }
        return null;
    }

    @CrossOrigin(origins = origin)
    @PostMapping(value = "/joinLobby", consumes = {"application/json"})
    @ResponseBody
    @ApiOperation("Join a specific lobby by their uuid")
    String joinLobby(@ApiParam("The serialized DataJoin Object. Cannot be null!") @RequestBody String lobbyRawDto) throws Exception {
        try {
            LobbySmallDto lobbyDto = gson.fromJson(lobbyRawDto, LobbySmallDto.class);
            if(isLobbySmallDtoValid(lobbyDto)) {
                return lobbyHandler.joinLobby(UUID.fromString(lobbyDto.getLobbyUuid()), lobbyDto.getPlayerName());
            }
            throw new InvalidJsonObjectException(lobbyRawDto);
        } catch (Exception ex) {
            throw new InvalidJsonObjectException(lobbyRawDto);
        }
    }

    private boolean isLobbyDtoValid(LobbyDto lobbyDto) {
        if(lobbyDto.getPlayerName() != null &&
        lobbyDto.getLobbyName() != null) {
            return true;
        }
        return false;
    }

    private boolean isLobbySmallDtoValid(LobbySmallDto lobbyDto) {
        if(lobbyDto.getPlayerName() != null &&
                lobbyDto.getLobbyUuid() != null) {
            return true;
        }
        return false;
    }

}