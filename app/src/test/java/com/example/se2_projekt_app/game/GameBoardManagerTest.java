package com.example.se2_projekt_app.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.networking.json.FieldUpdateMessage;
import com.example.se2_projekt_app.screens.User;
import com.example.se2_projekt_app.views.GameBoardView;
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
    private GameBoardView mockGameBoardView;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mockUser.getUsername()).thenReturn("Player1");
        when(mockUser2.getUsername()).thenReturn("Player2");
        gameBoardManager = new GameBoardManager(mockGameBoardView);
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


        User user = gameBoardManager.userExists("Player2");
        assertEquals(mockUser2, user, "User should be found in list");

        user = gameBoardManager.userExists("Player3");
        assertNull(user, "User should not be found in list");
    }

    @Test
    void testInitGameBoard() {
        gameBoardManager.initGameBoard(mockUser);
        assertEquals(1, gameBoardManager.getNumberOfUsers());
        gameBoardManager.initGameBoard(mockUser2);
        assertEquals(2, gameBoardManager.getNumberOfUsers());
        gameBoardManager.initGameBoard(mockUser2);
        assertEquals(2, gameBoardManager.getNumberOfUsers());
    }

    @Test
    void testShowGameBoard() {
        when(mockUser.getGameBoard()).thenReturn(mockGameBoard);

        gameBoardManager.addUser(mockUser);

        gameBoardManager.showGameBoard(mockUser.getUsername());

        verify(mockUser, times(2)).getGameBoard();
    }

    @Test
    void testUpdateUser() {
        GameBoard gameBoard = new GameBoard();
        Floor floor = new Floor(0, 0, FieldCategory.ENERGY);
        floor.addChamber(1);
        gameBoard.addFloor(floor);

        assertFalse(gameBoardManager.updateUser("Player10", ""));

        when(mockUser.getGameBoard()).thenReturn(gameBoard);

        String response = "{\"floor\":0, \"chamber\":0, \"field\":0, \"fieldValue\":\"FIVE\"}";

        assertEquals(FieldValue.NONE, gameBoard.getFloor(0).getChamber(0).getField(0).getNumber());
        assertTrue(gameBoardManager.updateUser(mockUser.getUsername(), response));
        assertEquals(FieldValue.FIVE, gameBoard.getFloor(0).getChamber(0).getField(0).getNumber());

    }

    @Test
    void testUpdateGameBoardFalse() {
        when(mockUser.getGameBoard()).thenReturn(null);
        assertFalse(gameBoardManager.updateGameBoard(mockUser, null));
    }


    @Test
    void testAcceptTurnFalse() {
        gameBoardManager.removeUser(mockUser);
        assertFalse(gameBoardManager.acceptTurn());
    }

    @Test
    void testAcceptTurnFalse2() {
        when(mockUser.getGameBoard()).thenReturn(null);
        assertFalse(gameBoardManager.acceptTurn());
    }

    @Test
    void testAcceptTurn() {
        GameBoard gameBoard = new GameBoard();
        Floor floor = new Floor(0, 0, FieldCategory.ENERGY);
        floor.addChamber(2);
        gameBoard.addFloor(floor);
        floor.handleClick(0, 0, mockGameBoardView, FieldValue.ONE);

        when(mockUser.getGameBoard()).thenReturn(gameBoard);
        assertTrue(gameBoardManager.acceptTurn());
        floor.handleClick(0, 0, mockGameBoardView, FieldValue.ONE);
        assertFalse(gameBoardManager.acceptTurn());
    }

    @Test
    void testGetLocalUsername(){
        assertEquals("Player1", gameBoardManager.getLocalUsername());
    }

}