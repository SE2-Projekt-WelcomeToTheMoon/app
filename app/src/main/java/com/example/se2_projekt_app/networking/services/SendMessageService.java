package com.example.se2_projekt_app.networking.services;

import android.util.Log;

import com.example.se2_projekt_app.networking.WebSocketClient;
import com.example.se2_projekt_app.screens.Username;

import org.json.JSONObject;

import lombok.SneakyThrows;

/**
 * Service to send messages to server.
 */
public class SendMessageService {
    private static final String TAG = "SendMessageService";
    private static final WebSocketClient webSocket = Username.webSocketClient;

    /**
     * Method to send one message to server.
     * @param messageToSend Message to send to server.
     */
    @SneakyThrows
    public static boolean sendMessage(JSONObject messageToSend){
        if(checkMessage(messageToSend)) {
            webSocket.sendMessageToServer(messageToSend.toString());
            Log.i(TAG, "Message sent.");
            return true;
        }else {
            Log.w(TAG, "Message incomplete. Message not sent.");
            return false;
        }
    }

    /**
     * Checks if must have keys are in the message.
     * @param messageToCheck Message to check.
     * @return Boolean value if message to send has needed keys or not.
     */
    @SneakyThrows
    private static boolean checkMessage(JSONObject messageToCheck){
        return (((messageToCheck.getString("action") != null) &&
                !(messageToCheck.getString("action").isEmpty())) &&
                ((messageToCheck.getString("username") != null)) &&
                !(messageToCheck.getString("username").isEmpty()));
    }
}
