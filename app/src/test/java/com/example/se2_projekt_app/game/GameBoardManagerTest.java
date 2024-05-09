package com.example.se2_projekt_app.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.screens.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GameBoardManagerTest {
    private GameBoardManager gameBoardManager;
    @Mock
    private User mockUser;
    @Mock
    private User mockUser2;
    @Mock
    private GameBoard mockGameBoard;
    @Mock
    private GameBoardManager mockGameBoardManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mockUser.getUsername()).thenReturn("User");
        gameBoardManager = new GameBoardManager();
        gameBoardManager.addUser(mockUser);
        doNothing().when(mockUser).setGameBoard(any(GameBoard.class));
    }

    @Test
    void testUserOperations() {
        assertEquals(1, gameBoardManager.getNumberOfUsers(), "User should be added to list");

        gameBoardManager.addUser(mockUser2);
        assertEquals(2, gameBoardManager.getNumberOfUsers(), "User should be added to list");

        gameBoardManager.removeUser(mockUser);
        assertEquals(1, gameBoardManager.getNumberOfUsers(), "User should be removed from list");

        when(mockUser2.getUsername()).thenReturn("User2");
        User user = gameBoardManager.userExists("User2");
        assertEquals(mockUser2, user, "User should be found in list");
    }

    @Test
    void testFullUpdateGameBoard() {
        GameBoard gameBoard = new GameBoard();
        ObjectMapper objectMapper = new ObjectMapper();
        Floor floor = new Floor(0,0,FieldCategory.ENERGY);
        gameBoard.addFloor(floor);

        String testResponse = null;
        try {
            testResponse = objectMapper.writeValueAsString(gameBoard);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        assertTrue(gameBoardManager.fullUpdateGameBoard(testResponse, "User"), "GameBoard should be updated");
        verify(mockUser).setGameBoard(any(GameBoard.class));
    }

    @Test
    void testGameBoardSerializationAndDeserialization() {
        ObjectMapper objectMapper = new ObjectMapper();
        GameBoard gameBoard = new GameBoard();
        Floor floor = new Floor(0,0,FieldCategory.ENERGY);
        gameBoard.addFloor(floor);

        String jsonOutput = null;
        try {
            jsonOutput = objectMapper.writeValueAsString(gameBoard);
            assertNotNull(jsonOutput);
            System.out.println("Serialized JSON: " + jsonOutput); // This helps verify the output format

            // Now test deserialization
            GameBoard deserializedGameBoard = objectMapper.readValue(jsonOutput, GameBoard.class);
            assertNotNull(deserializedGameBoard);
            // Additional checks can be added to verify the correctness of the deserialized object.
        } catch (JsonProcessingException e) {
            fail("Serialization or deserialization failed with exception: " + e.getMessage());
        }
    }

}
