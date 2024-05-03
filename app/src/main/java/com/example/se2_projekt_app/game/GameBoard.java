package com.example.se2_projekt_app.game;

import java.util.ArrayList;
import java.util.List;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.enums.RewardCategory;
import com.example.se2_projekt_app.networking.json.FieldUpdateMessage;

public class GameBoard {
    private List<Floor> floors;

    public GameBoard() {
        this.floors = new ArrayList<>();
        initializeGameBoard();
    }

    /**
     * PLACEHOLDER
     * Server will Provide Information at a later point
     */
    private void initializeGameBoard() {
        List<RewardCategory> rewards = new ArrayList<>();
        rewards.add(RewardCategory.PLANING);
        Floor floor = new Floor(0,0,FieldCategory.ENERGY);
        floor.addChamber(3, rewards);
        floor.addChamber(5, rewards);
        floors.add(floor);
        floor = new Floor(0,200,FieldCategory.WATER);
        floor.addChamber(3, rewards);
        floor.addChamber(8, rewards);
        floor.addChamber(2, rewards);
        floors.add(floor);
    }

    void updateGameBoard(FieldUpdateMessage fieldUpdateMessage) {
        int floor = fieldUpdateMessage.getFloor();
        int chamber = fieldUpdateMessage.getChamber();
        int field = fieldUpdateMessage.getField();
        int fieldValue = fieldUpdateMessage.getValue().getValue();


        this.floors.get(floor).getChamber(chamber).getField(field).setNumber(fieldValue);
    }
    public List<Floor> getFloors() {
        return new ArrayList<>(floors);
    }
}