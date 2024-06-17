package com.example.se2_projekt_app.game;

import android.util.Log;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.enums.GameState;
import com.example.se2_projekt_app.networking.json.ActionValues;
import com.example.se2_projekt_app.networking.json.FieldUpdateMessage;
import com.example.se2_projekt_app.networking.json.JSONKeys;
import com.example.se2_projekt_app.networking.json.JSONService;
import com.example.se2_projekt_app.networking.responsehandler.ResponseReceiver;
import com.example.se2_projekt_app.networking.services.SendMessageService;
import com.example.se2_projekt_app.screens.User;
import com.example.se2_projekt_app.screens.Username;
import com.example.se2_projekt_app.views.GameBoardView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;

public class GameBoardManager {

    @Getter
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
    private static final String SUCCESS = JSONKeys.SUCCESS.getValue();
    public static ResponseReceiver cheatResponseReceiver;
    public static ResponseReceiver cheatDetectResponseReceiver;
    private static final String TAG = "Gamescreen";
    private SendMessageService sendMessageService = new SendMessageService();
    private GameState currentGameState = null;

    public GameBoardManager(GameBoardView gameBoardView, CardController cardController) {
        this.gameBoardView = gameBoardView;
        this.objectMapper = new ObjectMapper();
        this.cardController = cardController;
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
        JSONObject jsonObject = JSONService.generateJSONObject("makeMove", localUsername, true, payload, "");
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
        return GameBoardView.getLastAccessedFloor().getLastAccessedChamber().getLastAccessedField();
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

    /***
     * Updates Current Carddraw
     */
    public void updateCurrentCardDraw() {
        JSONObject jsonObject = JSONService.generateJSONObject("updateCurrentCards", localUsername, true, "", "");
        SendMessageService.sendMessage(jsonObject);
    }

    public void setSelectedCard(CardCombination combination) {
        gameBoardView.setCurrentSelection(combination);
    }

    public boolean cheat() {
        String username = Username.user.getUsername();
        JSONObject msg = JSONService.generateJSONObject(
                ActionValues.CHEAT.getValue(), username, null, "",
                "");
        SendMessageService.sendMessage(msg);

        GameBoardManager.cheatResponseReceiver = response -> {
            boolean success = response.getBoolean(SUCCESS);
            if (success) {
                Log.i(TAG, "Cheated successfully");
            }
        };

        return true;
    }

    public void updateCheatedUser(String username, String cheatedUser) {
        Log.i("GameBoardManager", "Updating game board for User: " + username);

        User user = userExists(cheatedUser);

        if (user == null) {
            Log.e("GameBoardManager", "User does not exist: " + cheatedUser);
            return;
        }

        updateCheatGameBoard(user);

        if (cheatedUser.equals(localUsername)) {
            Log.i("GameBoardManager", "Updating game view for local user");
            updateGameBoardView(user);
        }

        Log.i("GameBoardManager", "GameBoard updated for User: " + cheatedUser);
    }

    public void updateSysErrorUser(String username, int sysError) {
        Log.i("GameBoardManager", "Updating game board for User: " + username);

        User user = userExists(username);

        updateSysErrorGameBoard(user, sysError);

        Log.i("GameBoardManager", "GameBoard updated for User: " + username);
    }

    public void addRocketUser(String username, int rocketCount) {
        // TODO: UPDATE UI SOMEHOW
        Log.i("GameBoardManager", "Updating game board for User: " + username);
        User user = userExists(username);
        user.getGameBoard().addRockets(rocketCount);
        Log.i("GameBoardManager", "GameBoard updated for User: " + username);
    }


    private void updateCheatGameBoard(User user) {
        GameBoard gameBoard = user.getGameBoard();
        if (gameBoard == null) {
            return;
        }
        gameBoard.addRockets(1);
        user.setGameBoard(gameBoard);
    }

    private void updateSysErrorGameBoard(User user, int sysError) {
        GameBoard gameBoard = user.getGameBoard();
        if (gameBoard == null) {
            return;
        }
        //gameBoard.addSysError(1);
        gameBoard.setSysError(sysError);

        user.setGameBoard(gameBoard);
    }

    public int getRocketsOfPlayer(String username) {
        User user = userExists(username);
        if (user != null && user.getGameBoard() != null) {
            return user.getGameBoard().getRockets();
        } else {
            Log.e("GameBoardManager", "User does not exist or has no GameBoard");
        }
        return -1;
    }

    public int getSysErrorOfPlayer(String username) {
        User user = userExists(username);
        if (user != null && user.getGameBoard() != null) {
            return user.getGameBoard().getSysError();
        } else {
            Log.e("GameBoardManager", "User does not exist or has no GameBoard");
        }
        return -1;
    }

    public void detectCheat(String currentOwner) {
        String username = Username.user.getUsername();
        JSONObject msg = JSONService.generateJSONObject(
                ActionValues.DETECTCHEAT.getValue(), username, null, currentOwner,
                "");
        SendMessageService.sendMessage(msg);

        GameBoardManager.cheatDetectResponseReceiver = response -> {
            boolean success = response.getBoolean(SUCCESS);
            if (success) {
                Log.i(TAG, "Detected Cheat successfully");
            }
        };
    }

    public void updateCorrectCheatDetection(String username, String detector, boolean success) {
        Log.i("GameBoardManager", "Updating game board for User: " + username);

        User user = userExists(detector);

        if (user == null) {
            Log.e("GameBoardManager", "User does not exist: " + detector);
            return;
        }

        updateDetectorGameBoard(user, success);

        if (detector.equals(localUsername)) {
            Log.i("GameBoardManager", "Updating game view for local user");
            updateGameBoardView(user);
        }

        Log.i("GameBoardManager", "GameBoard updated for User: " + detector);
    }

    private void updateDetectorGameBoard(User user, boolean success) {
        GameBoard gameBoard = user.getGameBoard();
        if (gameBoard == null) {
            return;
        }

        gameBoard.addRockets(success ? 1 : -1);
        if (success) {
            Log.i("GameBoardManager", "cheat detect successful");
        } else {
            Log.i("GameBoardManager", "cheat detect wrong");
        }

        user.setGameBoard(gameBoard);
    }

    public void setGameState(GameState gameState) {
        this.currentGameState = gameState;
    }

    public GameState getGameState() {
        return currentGameState;
    }

    public void updateChamberOutline() {
        // Convert currentCombination to a Set of FieldCategory
        Set<FieldCategory> activeCategories = Arrays.stream(cardController.getCurrentCombination())
                .map(CardCombination::getCurrentSymbol)
                .collect(Collectors.toSet());

        // Iterate over users
        for (User user : users) {
            // Iterate over floors in the user's game board
            for (Floor floor : user.getGameBoard().getFloors()) {
                // Deactivate all chambers
                for (Chamber chamber : floor.getChambers()) {
                    chamber.setInactive();
                }

                // Activate chambers if the floor's category is in activeCategories
                if (activeCategories.contains(floor.getCategory())) {
                    for (Chamber chamber : floor.getChambers()) {
                        chamber.setActive();
                    }
                }
            }
        }
    }


}
