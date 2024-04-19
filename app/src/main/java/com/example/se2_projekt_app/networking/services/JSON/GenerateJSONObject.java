package com.example.se2_projekt_app.networking.services.JSON;

import org.json.JSONObject;

import lombok.SneakyThrows;

public class GenerateJSONObject {
    /**
     *Method to generate a JSON O
     * object
     * @param action    Action to be run by backend
     * @param username  Username of client
     * @param success   If action was successfully run or not
     * @param message   Every possible message
     * @param error     Error message
     * @return         JSONObject
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
