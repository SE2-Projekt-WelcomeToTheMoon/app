package com.example.se2_projekt_app.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.se2_projekt_app.enums.FieldCategory;
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
    void testInitGameBoard_NewUser() {
        // Assuming no existing users
        when(gameBoardManager.userExists("Player1")).thenReturn(null);

        gameBoardManager.initGameBoard(mockUser);

        verify(mockUser, times(1)).setGameBoard(any(GameBoard.class));
        verify(mockGameBoardView, times(1)).setGameBoard(any(GameBoard.class));
    }



}
