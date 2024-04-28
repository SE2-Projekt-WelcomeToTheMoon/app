package com.example.se2_projekt_app.networking.responsehandler;

import android.util.Log;


import com.example.se2_projekt_app.screens.Debug;
import com.example.se2_projekt_app.screens.MainMenu;
import org.json.JSONException;
import com.example.se2_projekt_app.screens.Multiplayer;
import com.example.se2_projekt_app.screens.Username;


import org.json.JSONObject;

import lombok.SneakyThrows;

/**
 * Class to route received message from server to their corresponding views.
 */
public class PostOffice {
    private static final String TAG = "PostOffice";

    /**
     * Routes messages to screens according to their action key value.
     * @param response Response to route.
     */
    @SneakyThrows
    public void routeResponse(JSONObject response) throws JSONException {

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

            case "getNextCard":
                Debug.responseReceiver.receiveResponse(response);
                Log.i(TAG,"Rerouted message to Debug");
                break;

            case "leaveLobby":
                Multiplayer.responseReceiver.receiveResponse(response);
                Log.i(TAG, "Rerouted message to Multiplayer.");
                break;

            default:
                Log.w(TAG, "Server response has invalid or no sender. Response not routed.");
        }
    }
}
