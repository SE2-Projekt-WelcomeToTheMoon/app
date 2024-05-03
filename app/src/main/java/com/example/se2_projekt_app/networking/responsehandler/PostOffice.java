package com.example.se2_projekt_app.networking.responsehandler;

import android.util.Log;

import com.example.se2_projekt_app.game.GameBoard;
import com.example.se2_projekt_app.game.GameBoardManager;
import com.example.se2_projekt_app.screens.GameScreen;
import com.example.se2_projekt_app.screens.Multiplayer;
import com.example.se2_projekt_app.screens.Username;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

import lombok.SneakyThrows;

/**
 * Class to route received message from server to their corresponding views.
 */
public class PostOffice {
    private static final String TAG = "PostOffice";
    private static final String ERROR = "PostOffice Error";

    /**
     * Routes messages to screens according to their action key value.
     * @param response Response to route.
     */
    @SneakyThrows
    public void routeResponse(JSONObject response){

        String action = response.getString("action");

        switch(action){
            case "registerUser":
                Username.responseReceiver.receiveResponse(response);
                Log.i(TAG, "Rerouted message to Username view.");
                break;

            case "joinLobby":
                Multiplayer.responseReceiver.receiveResponse(response);
                Log.i(TAG, "Rerouted message to Multiplayer.");
                break;

            case "leaveLobby":
                Multiplayer.responseReceiver.receiveResponse(response);
                Log.i(TAG, "Rerouted message to Multiplayer.");
                break;

                // gets changes from other user in format of
            case "updateGameBoardSimple":
            case "updateGameBoardFull":
                try {
                    GameScreen.receiveResponse(response);
                } catch (JSONException e) {
                    Log.i(ERROR, "Error while parsing JSON object.");
                }
                Log.i(TAG, "Rerouted message to GameBoardManager.");
                break;

            default:
                Log.w(TAG, "Server response has invalid or no sender. Response not routed.");
        }
    }
}
