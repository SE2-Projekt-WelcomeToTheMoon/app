package com.example.se2_projekt_app.game;

import java.util.ArrayList;
import java.util.List;

import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.networking.json.FieldUpdateMessage;

import lombok.Getter;
import lombok.Setter;

public class GameBoard {
    private List<Floor> floors;

    @Getter
    private int rockets;

    @Getter
    @Setter
    private int sysError;
    public GameBoard() {
        this.floors = new ArrayList<>();
        rockets = 0;
        sysError = 0;
    }

    /**
     * PLACEHOLDER
     * Server will Provide Information at a later point
     */
    public void addFloor(Floor floor){
        this.floors.add(floor);
    }

    void updateGameBoard(FieldUpdateMessage fieldUpdateMessage) {
        int floor = fieldUpdateMessage.getFloor();
        int chamber = fieldUpdateMessage.getChamber();
        int field = fieldUpdateMessage.getField();
        FieldValue fieldValue = fieldUpdateMessage.getFieldValue();


        this.floors.get(floor).getChamber(chamber).getField(field).setNumber(fieldValue);
    }
    public Floor getFloor(int index) {
        return floors.get(index);
    }
    public List<Floor> getFloors() {
        return new ArrayList<>(floors);
    }

    public void addRockets(int amount){
        rockets += amount;
    }
    public void addSysError(int amount){
        sysError += amount;
    }
}