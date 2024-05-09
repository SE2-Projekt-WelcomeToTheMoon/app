package com.example.se2_projekt_app.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class GameBoardTest {

    @Test
    void testGameBoard() {
        GameBoard gameBoard = new GameBoard();
        assertNotNull(gameBoard.getFloors(), "GameBoard should have floors");
    }
}
