package com.example.se2_projekt_app.networking.JSON;

public enum ActionValues {
    REGISTERUSER("registerUser"),
    JOINLOBBY("joinLobby");

    private final String value;
    ActionValues(String action){
        this.value = action;
    }

    public String getValue() {
        return this.value;
    }
}
