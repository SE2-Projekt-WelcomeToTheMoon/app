package com.example.se2_projekt_app.networking.json;

import lombok.Getter;

/**
 * Enums for action key in JSON Objects sent to server to ensure that no
 * errors occur when routing sent message on server.
 */
@Getter
public enum ActionValues {
    REGISTERUSER("registerUser"),
    JOINLOBBY("joinLobby");

    private final String value;
    ActionValues(String action){
        this.value = action;
    }

}
