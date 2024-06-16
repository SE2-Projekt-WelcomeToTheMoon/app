package com.example.se2_projekt_app.game;

import com.example.se2_projekt_app.enums.FieldCategory;

public class GameBoardService {
    int floorIndex = 0;

    /**
     * Creates a new game board with predefined floors.
     *
     * @return The created game board.
     */
    public static GameBoard createGameBoard() {
        GameBoard board = new GameBoard();

        // Add floors to the game board
        createAndAddFloor(board, 0, 0, FieldCategory.SPACESUIT, new int[]{3});
        createAndAddFloor(board, 0, 200, FieldCategory.SPACESUIT, new int[]{3});
        createAndAddFloor(board, -200, 400, FieldCategory.WATER, new int[]{2, 3});
        createAndAddFloor(board, -200, 600, FieldCategory.ROBOT, new int[]{2, 3});
        createAndAddFloor(board, -200, 800, FieldCategory.ROBOT, new int[]{5});
        createAndAddFloor(board, -300, 1000, FieldCategory.PLANNING, new int[]{6});
        createAndAddFloor(board, -700, 1200, FieldCategory.ENERGY, new int[]{5, 2, 3});
        createAndAddFloor(board, -500, 1400, FieldCategory.PLANT, new int[]{2, 2, 2, 2});
        createAndAddFloor(board, -500, 1600, FieldCategory.WILDCARD, new int[]{2, 2, 2, 2});

        return board;
    }

    /**
     * Creates a new floor and adds it to the game board.
     *
     * @param board         The game board to add the floor to.
     * @param floorX        The X coordinate of the floor.
     * @param floorY        The Y coordinate of the floor.
     * @param category      The category of the floor.
     * @param floorChambers The chambers of the floor.
     */
    private static void createAndAddFloor(GameBoard board, int floorX, int floorY, FieldCategory category, int[] floorChambers) {
        Floor floor = new Floor(floorX, floorY, category);
        for (int chamber : floorChambers) {
            floor.addChamber(chamber);
        }
        board.addFloor(floor);
    }

}