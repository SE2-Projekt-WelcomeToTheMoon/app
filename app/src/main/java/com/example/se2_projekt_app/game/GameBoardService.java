package com.example.se2_projekt_app.game;

import com.example.se2_projekt_app.enums.FieldCategory;

public class GameBoardService {

    public GameBoardService() {
    }

    public static GameBoard createGameBoard() {
        GameBoard gameBoard = new GameBoard();
        Floor floor = new Floor(0, 0, FieldCategory.SPACESUIT);
        floor.addChamber(3);
        gameBoard.addFloor(floor);
        floor = new Floor(0, 200, FieldCategory.SPACESUIT);
        floor.addChamber(3);
        gameBoard.addFloor(floor);
        floor = new Floor(-200, 400, FieldCategory.WATER);
        floor.addChamber(2);
        floor.addChamber(3);
        gameBoard.addFloor(floor);
        floor = new Floor(-200, 600, FieldCategory.ROBOT);
        floor.addChamber(2);
        floor.addChamber(3);
        gameBoard.addFloor(floor);
        floor = new Floor(-200, 800, FieldCategory.ROBOT);
        floor.addChamber(5);
        gameBoard.addFloor(floor);
        floor = new Floor(-700, 1000, FieldCategory.PLANNING);
        floor.addChamber(5);
        floor.addChamber(2);
        floor.addChamber(3);
        gameBoard.addFloor(floor);
        floor = new Floor(-700, 1200, FieldCategory.ENERGY);
        floor.addChamber(5);
        floor.addChamber(2);
        floor.addChamber(3);
        gameBoard.addFloor(floor);
        floor = new Floor(-500, 1400, FieldCategory.PLANT);
        floor.addChamber(2);
        floor.addChamber(2);
        floor.addChamber(2);
        floor.addChamber(2);
        gameBoard.addFloor(floor);
        floor = new Floor(-500, 1600, FieldCategory.WILDCARD);
        floor.addChamber(2);
        floor.addChamber(2);
        floor.addChamber(2);
        floor.addChamber(2);
        gameBoard.addFloor(floor);

        return gameBoard;
    }

}
