package com.example.se2_projekt_app.networking.responsehandler;

import com.example.se2_projekt_app.screens.MainMenu;
import com.example.se2_projekt_app.screens.Multiplayer;
import org.json.JSONObject;
import lombok.SneakyThrows;

/**
 * Class to route received message from server to their corresponding views.
 */
public class PostOffice {

    /**
     * Routes messages to screens according to their action key value.
     * @param response Response to route.
     */
    @SneakyThrows
    public void routeResponse(JSONObject response){

        String action = response.getString("action");

        switch(action){
            case "registerUser":
                MainMenu.responseReceiver.receiveResponse(response);
                break;


            case "joinLobby":
                Multiplayer.responseReceiver.receiveResponse(response);
                break;

        }
    }
}
