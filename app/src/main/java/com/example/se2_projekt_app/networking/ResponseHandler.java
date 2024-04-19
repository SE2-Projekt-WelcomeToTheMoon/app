package com.example.se2_projekt_app.networking;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Interface to handle messages from server.
 * @param <T> Any object received from server.
 */
public interface ResponseHandler<T> {
    void onMessageReceived(JSONObject message) throws JSONException;
}
