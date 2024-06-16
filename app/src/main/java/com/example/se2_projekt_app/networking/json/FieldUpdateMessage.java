package com.example.se2_projekt_app.networking.json;

import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.game.CardCombination;
import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.Setter;

/**
 * This is a Skeleton for easy receiving Messages from Client in a proper Format
 */
@Getter
@Setter
public class FieldUpdateMessage {
    private int floor;
    private int chamber;
    private int field;
    private FieldValue fieldValue;
    private String userOwner;
    private CardCombination cardCombination;

    public FieldUpdateMessage(int floor, int chamber, int field, FieldValue value, String userOwner, CardCombination cardCombination) {
        this.floor = floor;
        this.chamber = chamber;
        this.field = field;
        this.fieldValue = value;
        this.userOwner = userOwner;
        this.cardCombination = cardCombination;
    }

    @JsonCreator
    public FieldUpdateMessage() {
        this.floor = 0;
        this.chamber = 0;
        this.field = 0;
        this.fieldValue = FieldValue.NONE;
        this.userOwner = "";
        this.cardCombination = null;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setChamber(int chamber) {
        this.chamber = chamber;
    }

    public void setField(int field) {
        this.field = field;
    }

    public void setFieldValue(FieldValue fieldValue) {
        this.fieldValue = fieldValue;
    }

    public void setUserOwner(String userOwner) {
        this.userOwner = userOwner;
    }

    public void setCardCombination(CardCombination cardCombination) {
        this.cardCombination = cardCombination;
    }
}