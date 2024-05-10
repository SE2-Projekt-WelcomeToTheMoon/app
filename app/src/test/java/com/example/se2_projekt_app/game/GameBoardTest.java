package com.example.se2_projekt_app.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.networking.json.FieldUpdateMessage;


class GameBoardTest {

    @Test
    void testGameBoard() {
        GameBoard gameBoard = new GameBoard();
        assertNotNull(gameBoard.getFloors(), "GameBoard should have floors");
    }

    @Test
    void testUpdateGameBoard() {
        GameBoard gameBoard = new GameBoard();
        Floor floor = new Floor(0, 0, FieldCategory.PLANNING);
        floor.addChamber(3);
        gameBoard.addFloor(floor);
        FieldUpdateMessage fieldUpdateMessage = new FieldUpdateMessage(0, 0, 0, FieldValue.ONE);
        gameBoard.updateGameBoard(fieldUpdateMessage);
        assertEquals(FieldValue.ONE, gameBoard.getFloors().get(0).getChamber(0).getField(0).getNumber(), "Field should be updated");
    }
}
