package com.example.se2_projekt_app.networking.responsehandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Interface to receive response from PostOffice class.
 */
public interface ResponseReceiver {

    /**
     * Method is being implemented in every view where response handling is needed.
     * @param response Response from server.
     * @throws JSONException Exception for JSON Object.
     */
    void receiveResponse(JSONObject response) throws JSONException;
}
