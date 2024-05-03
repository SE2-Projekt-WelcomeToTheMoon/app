package com.example.se2_projekt_app.networking.json;

/**
 * Enums for action key in JSON Objects sent to server to ensure that no
 * errors occur when routing sent message on server.
 */
public enum ActionValues {
    REGISTERUSER("registerUser"),
    JOINLOBBY("joinLobby"),
    LEAVELOBBY("leaveLobby"),
    UPDATESIMPLE("updateGameBoardSimple"),
    UPDATEFULL("updateGameBoardFull");

    private final String value;

    ActionValues(String action) {
        this.value = action;
    }

    public String getValue() {
        return value;
    }
}
