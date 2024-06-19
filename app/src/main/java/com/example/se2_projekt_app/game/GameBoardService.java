package com.example.se2_projekt_app.game;

import com.example.se2_projekt_app.enums.FieldCategory;

public class GameBoardService {

    private GameBoardService() {
    }
    /**
     * Creates a new game board with predefined floors.
     *
     * @return The created game board.
     */
    public static GameBoard createGameBoard() {
        GameBoard board = new GameBoard();

        // Add floors to the game board
        createAndAddFloor(board, 0, 0, FieldCategory.SPACESUIT, new int[]{3});
        createAndAddFloor(board, 0, 300, FieldCategory.SPACESUIT, new int[]{3});
        createAndAddFloor(board, -200, 600, FieldCategory.WATER, new int[]{2, 3});
        createAndAddFloor(board, -200, 900, FieldCategory.ROBOT, new int[]{2, 3});
        createAndAddFloor(board, -200, 1200, FieldCategory.ROBOT, new int[]{5});
        createAndAddFloor(board, -300, 1500, FieldCategory.PLANNING, new int[]{6});
        createAndAddFloor(board, -700, 1800, FieldCategory.ENERGY, new int[]{5, 2, 3});
        createAndAddFloor(board, -500, 2100, FieldCategory.PLANT, new int[]{2, 2, 2, 2});
        createAndAddFloor(board, -500, 2400, FieldCategory.WILDCARD, new int[]{2, 2, 2, 2});
        injectRewards(board);
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

    /**
     * Updates the Chambers so each one has the same rewards as the Serverside
     * @param gameBoard
     */
    private static void injectRewards(GameBoard gameBoard){
        gameBoard.getFloors().get(0).injectRewards(Reward.getFirstFloorRewards());
        gameBoard.getFloors().get(1).injectRewards(Reward.getSecondFloorRewards());
        gameBoard.getFloors().get(2).injectRewards(Reward.getThirdFloorRewards());
        gameBoard.getFloors().get(3).injectRewards(Reward.getFourthFloorRewards());
        gameBoard.getFloors().get(4).injectRewards(Reward.getFifthFloorRewards());
        gameBoard.getFloors().get(5).injectRewards(Reward.getSixthFloorRewards());
        gameBoard.getFloors().get(6).injectRewards(Reward.getSeventhFloorRewards());
        gameBoard.getFloors().get(7).injectRewards(Reward.getEighthFloorRewards());
        gameBoard.getFloors().get(8).injectRewards(Reward.getNinthFloorRewards());
    }

}