package com.example.se2_projekt_app.game;

import android.util.Log;

import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.enums.GameState;
import com.example.se2_projekt_app.networking.json.ActionValues;
import com.example.se2_projekt_app.networking.json.FieldUpdateMessage;
import com.example.se2_projekt_app.networking.json.JSONService;
import com.example.se2_projekt_app.networking.services.SendMessageService;
import com.example.se2_projekt_app.screens.User;
import com.example.se2_projekt_app.views.GameBoardView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class GameBoardManager {
    private final List<User> users = new ArrayList<>();
    private final GameBoardView gameBoardView;
    // this username is the username choosen by the local device
    private String localUsername = "Player1";
    private int floorIndex;
    private int chamberIndex;
    private int fieldIndex;
    private final CardController cardController;
    private ObjectMapper objectMapper;
    private final GameBoard emptyBoard = new GameBoard();
    private SendMessageService sendMessageService = new SendMessageService();
    private GameState currentGameState = null;

    public GameBoardManager(GameBoardView gameBoardView,CardController cardController) {
        this.gameBoardView = gameBoardView;
        this.objectMapper = new ObjectMapper();
        this.cardController=cardController;
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
        if (userExists(user.getUsername()) != null) {
            Log.e("GameBoardManager", "User already exists");
            return;
        }
        if (user.getLocalUser()) {
            this.localUsername = user.getUsername();
            Log.d("GameBoardManager", "Local User set to: " + localUsername);
        }

        GameBoard gameBoard = GameBoardService.createGameBoard();
        user.setGameBoard(gameBoard);
        this.users.add(user);

        if (user.getUsername().equals(localUsername)) {
            gameBoardView.setGameBoard(user.getGameBoard());
        }
    }

    public void showGameBoard(String username) {
        User user = userExists(username);
        if (user != null && user.getGameBoard() != null) {
            gameBoardView.setGameBoard(user.getGameBoard());
        } else {
            gameBoardView.setGameBoard(emptyBoard);
            Log.e("GameBoardManager", "User does not exist or has no GameBoard");
        }
    }

    public boolean updateUser(String username, String response) {
        Log.i("GameBoardManager", "Updating game board for User: " + username);
        FieldUpdateMessage fieldUpdateMessage = parseFieldUpdateMessage(response);

        String workingUsername = fieldUpdateMessage.getUserOwner();
        User user = userExists(workingUsername);

        if (user == null) {
            Log.e("GameBoardManager", "User does not exist: " + workingUsername);
            return false;
        }

        updateGameBoard(user, fieldUpdateMessage);

        if (workingUsername.equals(localUsername)) {
            Log.i("GameBoardManager", "Updating game view for local user");
            updateGameBoardView(user);
        }

        Log.i("GameBoardManager", "GameBoard updated for User: " + workingUsername);
        return true;


    }

    private FieldUpdateMessage parseFieldUpdateMessage(String response) {
        try {
            return objectMapper.readValue(response, FieldUpdateMessage.class);
        } catch (JsonProcessingException e) {
            Log.e("JSON Parsing Error", "Detailed Error: " + e.getOriginalMessage());
            throw new RuntimeException(e);
        }
    }

    private void updateGameBoardView(User user) {
        if (user.getUsername().equals(localUsername)) {
            gameBoardView.setGameBoard(user.getGameBoard());
        }
    }

    public boolean updateGameBoard(User user, FieldUpdateMessage fieldUpdateMessage) {
        GameBoard gameBoard = user.getGameBoard();
        if (gameBoard == null) {
            return false;
        }

        int floor = fieldUpdateMessage.getFloor();
        int chamber = fieldUpdateMessage.getChamber();
        int field = fieldUpdateMessage.getField();
        FieldValue fieldValue = fieldUpdateMessage.getFieldValue();

        gameBoard.getFloor(floor).getChamber(chamber).getField(field).setNumber(fieldValue);

        user.setGameBoard(gameBoard);
        return true;
    }

    /**
     * Accepts the turn of the current user and sends the updated field to the backend.
     * It will send the last accessed Field of the current user.
     * When the last Accessed Field was double tapped to undo the click it wont register the field as changed.
     * If the field is already finalized, not changed or null it will not send the field to the backend.
     */
    public boolean acceptTurn() {
//        Log.d("GameBoardManager", "Accepting Turn");
//        if (currentGameState != GameState.ROUND_THREE) {
//            Log.d("GameBoardManager", "Not in round three, asking for new State from Server");
//            JSONObject jsonObject = JSONService.generateJSONObject("sendGameState", "", true, "", "");
//            SendMessageService.sendMessage(jsonObject);
//            return false;
//        }
        User user = userExists(localUsername);
        if (user == null) {
            return false;
        }

        GameBoard gameBoard = user.getGameBoard();
        Field field = getLastAccessedField(gameBoard);
        if (field == null) {
            return false;
        }

        updateIndex();
        Log.d("GameBoardManager", "Field finalized: " + floorIndex + " " + chamberIndex + " " + fieldIndex + " " + field.getNumber());

        String payload = createPayload(field);
        JSONObject jsonObject = JSONService.generateJSONObject(ActionValues.MAKEMOVE.getValue(), localUsername, true, payload, "");
        SendMessageService.sendMessage(jsonObject);

        Log.d("GameBoardManager", "Payload: " + payload);
        return true;
    }

    public String createPayload(Field field) {
        CardCombination cardCombination = gameBoardView.getCurrentSelection();
        FieldUpdateMessage fieldUpdateMessage = new FieldUpdateMessage(floorIndex, chamberIndex, fieldIndex, field.getNumber(), localUsername, cardCombination);
        try {
            return objectMapper.writeValueAsString(fieldUpdateMessage);
        } catch (Exception e) {
            Log.e("GameBoardManager", "Error while converting FieldUpdateMessage to JSON");
            e.printStackTrace();
            return "";
        }
    }

    private void updateIndex() {
        floorIndex = gameBoardView.getLastAccessedFloorIndex();
        chamberIndex = gameBoardView.getLastAccessedFloor().getLastAccessedChamberIndex();
        fieldIndex = gameBoardView.getLastAccessedFloor().getLastAccessedChamber().getLastAccessedFieldIndex();
    }

    public Field getLastAccessedField(GameBoard gameBoard) {
        if (gameBoard == null) {
            Log.e("GameBoardManager", "GameBoard is null");
            return null;
        }
        Field field = GameBoardView.getLastAccessedFloor().getLastAccessedChamber().getLastAccessedField();
        return field;
    }

    public String getLocalUsername() {
        return localUsername;
    }

    public void setLocalUsername(String localUsername) {
        this.localUsername = localUsername;
    }

    public int getNumberOfUsers() {
        return users.size();
    }
    // for testing only
    public void setSendMessageService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    public Floor getLastAccessedFloor() {
        return GameBoardView.getLastAccessedFloor();
    }

    public void displayCurrentCombination() {
        cardController.displayCurrentCombination();
    }
    public void extractCardsFromServerString(String message) {
        cardController.extractCardsFromServerString(message);
    }

    public void setSelectedCard(CardCombination combination) {
        gameBoardView.setCurrentSelection(combination);
    }

    public void setGameState(GameState gameState) {
        this.currentGameState = gameState;
    }
    public GameState getGameState() {
        return currentGameState;
    }
}
