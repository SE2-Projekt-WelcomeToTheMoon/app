package com.example.se2_projekt_app.networking.json;

import com.example.se2_projekt_app.enums.FieldValue;

/**
 * This is a Skeleton for easy receiving Messages from Client in a proper Format
 */
public class FieldUpdateMessage {
    private final int floor;
    public final int chamber;
    public final int field;
    public final FieldValue fieldValue;

    public FieldUpdateMessage(int floor, int chamber, int field, FieldValue value) {
        this.floor = floor;
        this.chamber = chamber;
        this.field = field;
        this.fieldValue = value;
    }

    public int getFloor() {
        return this.floor;
    }

    public int getChamber() {
        return this.chamber;
    }

    public int getField() {
        return this.field;
    }

    public FieldValue getFieldValue() {
        return this.fieldValue;
    }
}