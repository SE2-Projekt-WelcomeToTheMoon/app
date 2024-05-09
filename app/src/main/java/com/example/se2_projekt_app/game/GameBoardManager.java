package com.example.se2_projekt_app.game;

import com.example.se2_projekt_app.networking.json.FieldUpdateMessage;
import com.example.se2_projekt_app.networking.json.JSONService;
import com.example.se2_projekt_app.screens.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class GameBoardManager {
    private final List<User> users = new ArrayList<>();

    public GameBoardManager() {

    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public User userExists(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean fullUpdateGameBoard(String response, String username) {
        User user = userExists(username);
        if (user == null) {
            return false;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            GameBoard gameBoard = objectMapper.readValue(response, GameBoard.class);
            user.setGameBoard(gameBoard);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getNumberOfUsers() {
        return users.size();
    }

}
