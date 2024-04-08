package com.example.se2_projekt_app.networking;

import org.json.JSONObject;

/**
 * Listener interface for messages received from server.
 */
public interface ServerResponseListener {
    void onResponseReceived(JSONObject response);
}