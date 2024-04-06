package com.example.se2_projekt_app.networking.JSON;

import lombok.Getter;
@Getter
public enum ActionValues {
    REGISTERUSER("registerUser"),
    JOINLOBBY("joinLobby");

    private final String value;
    ActionValues(String action){
        this.value = action;
    }
}
