package com.example.se2_projekt_app.networking.lobby;

import com.example.se2_projekt_app.screens.User;

import java.util.ArrayList;

import lombok.Getter;

/**
 * Singleton class to manage the lobby.
 */
@Getter
public class LobbyManager {
    private static final LobbyManager ourInstance = new LobbyManager();
    private final ArrayList<User> users = new ArrayList<>();

    private LobbyManager() {
    }
    public static LobbyManager getInstance() {
        return ourInstance;
    }

}
