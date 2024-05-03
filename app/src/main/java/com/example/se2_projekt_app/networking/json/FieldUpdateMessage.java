package com.example.se2_projekt_app.networking.json;


import com.example.se2_projekt_app.enums.FieldValue;

import lombok.Getter;

/**
 * This is a Skeleton for easy receiving Messages from Client in a proper Format
 */

public class FieldUpdateMessage {
    @Getter
    private final int floor;
    @Getter
    private final int chamber;
    @Getter
    private final int field;
    @Getter
    private final FieldValue value;

    public FieldUpdateMessage(int floor, int chamber, int field, FieldValue value){
        this.floor = floor;
        this.chamber = chamber;
        this.field = field;
        this.value = value;
    }

    public int getFloor() {
        return floor;
    }
    public int getChamber() {
        return chamber;
    }
    public int getField() {
        return field;
    }
    public FieldValue getValue() {
        return value;
    }
}