package com.chess4you.lobbyserver.controller;

import com.chess4you.lobbyserver.exceptionHandling.exception.FormDataNotValidException;
import com.chess4you.lobbyserver.handler.LobbyHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;


@Slf4j
@RestController
@Api(description = "Controller for creating or retrieving Lobbies")
public class LobbyController {

    private LobbyHandler lobbyHandler;

    @Autowired
    LobbyController(LobbyHandler lobbyHandler) {
        this.lobbyHandler = lobbyHandler;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getAllLobbies")
    @ApiOperation("Return a List of all Lobbies")
    String getListLobby(){
        return lobbyHandler.getLobbies();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getLobby")
    @ResponseBody
    @ApiOperation("Return a specific lobby by their uuid.")
    String getLobby(@ApiParam("The uuid of the lobby Cannot be null!") @RequestParam("lobbyUuid") String lobbyUuid) throws Exception {
        return lobbyHandler.getLobby(UUID.fromString(lobbyUuid));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/initLobby", consumes = {"application/x-www-form-urlencoded"})
    @ApiOperation("Initialize a lobby")
    String initLobby(@ApiParam("The serialized DataInit Object. Cannot be null!") @RequestParam Map<String, String> formData) throws Exception {
        if(isInitFormValid(formData)) {
            return lobbyHandler.initLobby(formData.get("lobbyName"), formData.get("playerName"), Integer.parseInt(formData.get("chooseColor")));
        }
        throw new FormDataNotValidException(formData);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/joinLobby", consumes = {"application/x-www-form-urlencoded"})
    @ResponseBody
    @ApiOperation("Join a specific lobby by their uuid")
    String joinLobby(@ApiParam("The serialized DataJoin Object. Cannot be null!") @RequestParam Map<String, String> formData) throws Exception {
        if(isJoinFormValid(formData)) {
            return lobbyHandler.joinLobby(UUID.fromString(formData.get("lobbyUuid")), formData.get("playerName"));
        }
        throw new FormDataNotValidException(formData);
    }

    private boolean isInitFormValid(Map<String, String> formData) {
        if(formData.size() == 3
                && formData.containsKey("lobbyName")
                && formData.containsKey("playerName")
                && formData.containsKey("chooseColor")) {
            for(var entry : formData.values()) {
                if(entry.isEmpty()) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean isJoinFormValid(Map<String, String> formData) {
        if(formData.size() == 2
                && formData.containsKey("lobbyUuid")
                && formData.containsKey("playerName")) {
            for(var entry : formData.values()) {
                if(entry.isEmpty()) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

}