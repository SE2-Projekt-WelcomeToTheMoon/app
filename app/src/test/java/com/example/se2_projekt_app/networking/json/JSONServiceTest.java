package com.example.se2_projekt_app.networking.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class JSONServiceTest {

    @Test
    void testGenerateJSONObject() throws JSONException {
        String action = "testAction";
        String username = "testUser";
        Boolean success = true;
        String message = "testMessage";
        String error = "testError";

        JSONObject result = JSONService.generateJSONObject(action, username, success,
                message, error);

        assertEquals(action, result.get("action"));
        assertEquals(username, result.get("username"));
        assertEquals(success, result.get("success"));
        assertEquals(message, result.get("message"));
        assertEquals(error, result.get("error"));
    }

    @Test
    void testGenerateJSONObjectWithNullValues() {
        JSONObject result = JSONService.generateJSONObject(null, null,
                null, null, null);

        assertFalse(result.has("action"));
        assertFalse(result.has("username"));
        assertFalse(result.has("success"));
        assertFalse(result.has("message"));
        assertFalse(result.has("error"));
    }

    @Test
    void testGenerateJSONObjectWithEmptyStrings() {
        JSONObject result = JSONService.generateJSONObject("", "",
                null, "", "");

        assertTrue(result.has("action"));
        assertTrue(result.has("username"));
        assertFalse(result.has("success"));
        assertFalse(result.has("message"));
        assertFalse(result.has("error"));
    }

}
