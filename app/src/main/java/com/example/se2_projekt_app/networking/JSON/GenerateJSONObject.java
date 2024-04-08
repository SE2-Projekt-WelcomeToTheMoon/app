package com.example.se2_projekt_app.networking.JSON;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class to generate a generic JSON object for proper communication with server.
 */
public class GenerateJSONObject {

    /**
     * Returns a JSON object with a fixed pattern.
     * @return initialised JSON object.
     * @throws JSONException
     */
    public static JSONObject generateJSONObject() throws JSONException {
        return new JSONObject("{\"username\":\"\",\"action\":\"\",\"message\":\"\"}");
    }
}
