package com.example.se2_projekt_app.game;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.networking.json.FieldUpdateMessage;
import com.example.se2_projekt_app.networking.json.JSONService;
import com.example.se2_projekt_app.screens.User;
import com.example.se2_projekt_app.views.GameBoardView;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@SuppressWarnings("all")
public class GameBoardManager {
    private final List<User> users = new ArrayList<>();
    private final GameBoardView gameBoardView;
    // this username is the username choosen by the local device
    private String localUsername = "Player1";

    public GameBoardManager(GameBoardView gameBoardView) {
        this.gameBoardView = gameBoardView;
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

    public void initGameBoard(User user) {
        User existingUser = userExists(user.getUsername());
        if (existingUser == null) {
            GameBoard gameBoard = new GameBoard();
            Floor floor;
            switch (user.getUsername()) {
                case "Player1":
                    floor = new Floor(0, 0, FieldCategory.ENERGY);
                    floor.addChamber(5);
                    break;
                case "Player2":
                    floor = new Floor(0, 0, FieldCategory.ROBOT);
                    floor.addChamber(3);
                    break;
                case "Player3":
                    floor = new Floor(0, 0, FieldCategory.PLANT);
                    floor.addChamber(2);
                    break;
                case "Player4":
                    floor = new Floor(0, 0, FieldCategory.PLANNING);
                    floor.addChamber(1);
                    break;
                default:
                    floor = new Floor(0, 0, FieldCategory.PLANNING);
                    floor.addChamber(1);
            }
            gameBoard.addFloor(floor);
            user.setGameBoard(gameBoard);
            this.users.add(user);
        } else {
            user = existingUser;
        }
        gameBoardView.setGameBoard(user.getGameBoard());
    }


    public void showGameBoard(String username) {
        User user = userExists(username);
        if (user != null && user.getGameBoard() != null) {
            gameBoardView.setGameBoard(user.getGameBoard());
        }
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
            if (user.getUsername().equals(localUsername)) {
                gameBoardView.setGameBoard(gameBoard);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getLocalUsername() {
        return localUsername;
    }

    public int getNumberOfUsers() {
        return users.size();
    }

}
