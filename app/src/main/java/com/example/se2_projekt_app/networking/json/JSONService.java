package com.example.se2_projekt_app.networking.json;

import org.json.JSONObject;

import lombok.SneakyThrows;


public class JSONService {

    private String action;
    private String username;
    private Boolean success = false;
    private String message;
    private String error;

    /**
     * @param action    Aktion die ausgeführt werden soll
     * @param username  Benutzername
     * @param success   Aktion erfolgreich oder nicht
     * @param message   Nachricht
     * @param error     Fehlermeldung
     */
    public JSONService(String action, String username, Boolean success, String message, String error){
        this.action = action;
        this.username = username;
        this.success = success;
        this.message = message;
        this.error = error;
    }

    /**
     * Method to generate a JSON Object
     * @return JSONObject
     */
    @SneakyThrows
    public JSONObject generateJSONObject() {
        JSONObject response = new JSONObject();

        if (this.action != null) {
            response.put("action", this.action);
        }

        if (this.username != null) {
            response.put("username", this.username);
        }

        if(this.success != null){
            response.put("success", this.success);
        }

        //nur wenn String leer ist
        if (this.message != null && !message.isEmpty()) {
            response.put("message", this.message);
        }

        if (this.error != null && !error.isEmpty()) {
            response.put("error", this.error);
        }

        return response;
    }
}
