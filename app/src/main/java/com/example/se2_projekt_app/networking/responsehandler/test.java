package com.example.se2_projekt_app.networking.responsehandler;

import org.json.JSONObject;

import lombok.SneakyThrows;

public class test {

    @SneakyThrows
    public static JSONObject generateJSON(String action, String username, Boolean success, String message, String error) {
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
