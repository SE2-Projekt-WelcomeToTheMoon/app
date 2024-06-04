package com.example.se2_projekt_app.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.networking.json.FieldUpdateMessage;


class GameBoardTest {
    private GameBoard gameBoard;

    @BeforeEach
    void setUp() {
        this.gameBoard = new GameBoard();
    }

    @Test
    void testGameBoard() {
        assertNotNull(gameBoard.getFloors(), "GameBoard should have floors");
    }

    @Test
    void testUpdateGameBoard() {
        Floor floor = new Floor(0, 0, FieldCategory.PLANNING);
        floor.addChamber(3);
        gameBoard.addFloor(floor);
        FieldUpdateMessage fieldUpdateMessage = new FieldUpdateMessage(0, 0, 0, FieldValue.ONE, "");
        gameBoard.updateGameBoard(fieldUpdateMessage);
        assertEquals(FieldValue.ONE, gameBoard.getFloors().get(0).getChamber(0).getField(0).getNumber(), "Field should be updated");
    }

    @Test
    void testGetFloor() {
        Floor floor = new Floor(0, 0, FieldCategory.PLANNING);
        gameBoard.addFloor(floor);
        assertEquals(floor, gameBoard.getFloor(0), "Floor should be returned");
    }

    @Test
    public void testAddRockets() {
        gameBoard.addRockets(5);
        assertEquals(5, gameBoard.getRockets());

        gameBoard.addRockets(3);
        assertEquals(8, gameBoard.getRockets());
    }
}
