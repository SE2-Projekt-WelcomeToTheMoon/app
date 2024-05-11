package com.example.se2_projekt_app.game;

import android.util.Log;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.networking.json.FieldUpdateMessage;
import com.example.se2_projekt_app.networking.json.JSONService;
import com.example.se2_projekt_app.screens.User;
import com.example.se2_projekt_app.views.GameBoardView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
    private int floorIndex;
    private int chamberIndex;
    private int fieldIndex;
    private ObjectMapper objectMapper;

    public GameBoardManager(GameBoardView gameBoardView) {
        this.gameBoardView = gameBoardView;
        this.objectMapper = new ObjectMapper();
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
            GameBoard gameBoard = GameBoardService.createGameBoard();
            user.setGameBoard(gameBoard);
            this.users.add(user);
        } else {
            user = existingUser;
        }
        if (user.getUsername().equals(localUsername)) {
            gameBoardView.setGameBoard(user.getGameBoard());
        }
    }


    public void showGameBoard(String username) {
        User user = userExists(username);
        if (user != null && user.getGameBoard() != null) {
            gameBoardView.setGameBoard(user.getGameBoard());
        }
    }

    /**
     * maybe legacy
     *
     * @param response
     * @param username
     * @return
     */
    public boolean fullUpdateGameBoard(String response, String username) {
        User user = userExists(username);
        if (user == null) {
            Log.e("GameBoardManager", "User does not exist");
            return false;
        }

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

    public void updateUser(String username, String response) {
        User user = userExists(username);
        if (user == null) {
            Log.e("GameBoardManager", "User does not exist");
            return;
        }
        try {
            FieldUpdateMessage fieldUpdateMessage = objectMapper.readValue(response, FieldUpdateMessage.class);
            updateGameBoard(user, fieldUpdateMessage);
            if (user.getUsername().equals(localUsername)) {
                gameBoardView.setGameBoard(user.getGameBoard());
                Log.i("GameBoardManager", "Updated GameBoard for local user");
            }
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateGameBoard(User user, FieldUpdateMessage fieldUpdateMessage) {
        GameBoard gameBoard = user.getGameBoard();
        int floor = fieldUpdateMessage.getFloor();
        int chamber = fieldUpdateMessage.getChamber();
        int field = fieldUpdateMessage.getField();
        FieldValue fieldValue = fieldUpdateMessage.getFieldValue();
        gameBoard.getFloor(floor).getChamber(chamber).getField(field).setNumber(fieldValue);
        user.setGameBoard(gameBoard);
    }

    /**
     * Accepts the turn of the current user and sends the updated field to the backend.
     * It will send the last accessed Field of the current user.
     * When the last Accessed Field was double tapped to undo the click it wont register the field as changed.
     * If the field is already finalized, not changed or null it will not send the field to the backend.
     */
    public void acceptTurn() {
        User user = userExists(localUsername);
        if (user == null) {
            return;
        }

        GameBoard gameBoard = user.getGameBoard();
        Field field = getLastAccessedField(gameBoard);
        if (field == null) {
            return;
        }

        field.setFinalized();
        Log.d("GameBoardManager", "Field finalized: " + floorIndex + " " + chamberIndex + " " + fieldIndex + " " + field.getNumber());
        FieldUpdateMessage fieldUpdateMessage = new FieldUpdateMessage(floorIndex, chamberIndex, fieldIndex, field.getNumber());

        String payload = "";
        try {
            payload = objectMapper.writeValueAsString(fieldUpdateMessage);
        } catch (Exception e) {
            Log.e("GameBoardManager", "Error while converting FieldUpdateMessage to JSON");
            e.printStackTrace();
        }
        JSONService.generateJSONObject("updateUser", localUsername, true, payload, "");

        Log.d("GameBoardManager", "Payload: " + payload);

    }

    private Field getLastAccessedField(GameBoard gameBoard) {
        int floorIndex = gameBoardView.getLastAccessedFloor();
        Floor floor = gameBoard.getFloor(floorIndex);
        int chamberIndex = floor.getLastAccessedChamber();
        Chamber chamber = floor.getChamber(chamberIndex);
        int fieldIndex = chamber.getLastAccessedField();
        Field field = chamber.getField(fieldIndex);
        if (!field.isFinalized() && field.isChanged()) {
            this.floorIndex = floorIndex;
            this.chamberIndex = chamberIndex;
            this.fieldIndex = fieldIndex;
            return field;
        }
        Log.e("GameBoardManager", "Field is already finalized, not changed or null");
        return null;
    }

    public String getLocalUsername() {
        return localUsername;
    }

    public int getNumberOfUsers() {
        return users.size();
    }

}
