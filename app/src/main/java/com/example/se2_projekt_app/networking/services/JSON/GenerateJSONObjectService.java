package com.example.se2_projekt_app.networking.services.JSON;

import org.json.JSONObject;
import lombok.SneakyThrows;

/**
 * Class to generate a generic JSON object for proper communication with server.
 */
public class GenerateJSONObjectService {

    /**
     * Returns a JSON object with a fixed pattern.
     * @param action    Action that should be run by backend
     * @param username  Name of the client
     * @param success   Boolean if the action requested succeeded or not
     * @param message   Any message
     * @param error     Error message
     * @return initialised JSON object.
     */
    @SneakyThrows
    public static JSONObject generateJSONObject(String action, String username, Boolean success, String message, String error) {
        JSONObject response = new JSONObject();
        if (action != null) {
            response.put("action", action);
        }
        if (username != null) {
            response.put("username", username);
        }
        if (success != null) {
            response.put("success", success);
        }
        //nur wenn String leer ist
        if (message != null && !message.isEmpty()) {
            response.put("message", message);
        }
        if (error != null && !error.isEmpty()) {
            response.put("error", error);
        }

        return response;
    }
}
