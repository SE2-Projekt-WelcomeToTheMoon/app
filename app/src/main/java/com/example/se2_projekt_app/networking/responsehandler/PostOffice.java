package com.example.se2_projekt_app.networking.responsehandler;

import android.util.Log;

import com.example.se2_projekt_app.game.GameBoardManager;
import com.example.se2_projekt_app.screens.GameScreen;
import com.example.se2_projekt_app.screens.Multiplayer;
import com.example.se2_projekt_app.screens.Username;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.SneakyThrows;

/**
 * Class to route received message from server to their corresponding views.
 */
public class PostOffice {
    private static final String TAG = "PostOffice";
    private static final String ERROR = "PostOffice Error";
    private static final String MULTIPLAYER = "Rerouted message to Multiplayer.";

    /**
     * Routes messages to screens according to their action key value.
     *
     * @param response Response to route.
     */
    @SneakyThrows
    public void routeResponse(JSONObject response) throws JSONException {

        String action = response.getString("action");

        switch (action) {
            case "registerUser":
                Username.responseReceiver.receiveResponse(response);
                Log.i(TAG, "Rerouted message to Username view.");
                break;

            case "joinLobby":
            case "leaveLobby":
            case "requestLobbyUser":
                Multiplayer.responseReceiver.receiveResponse(response);
                Log.i(TAG, MULTIPLAYER);
                break;
            case "gameIsStarted":
                Multiplayer.startGameResponseReceiver.receiveResponse(response);
                Log.i(TAG, MULTIPLAYER);
                break;
            case "updateUser":
            case "makeMove":
            case "playerHasCheated":
                GameScreen.responseReceiver.receiveResponse(response);
                Log.i(TAG, "Rerouted message to GameScreen.");
                break;

            case "cheat":
                GameBoardManager.cheatResponseReceiver.receiveResponse(response);
                Log.i(TAG, "Rerouted cheat message to GameBoardManager.");
                break;

            default:
                Log.w(ERROR, "Server response has invalid or no sender. Response not routed.");
        }
    }
}
