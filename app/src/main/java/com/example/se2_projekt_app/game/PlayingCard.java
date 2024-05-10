package com.example.se2_projekt_app.game;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;

import lombok.Getter;

@Getter
public class PlayingCard{

    private final FieldCategory symbol;
    private final FieldValue number;

    public PlayingCard(FieldCategory symbol, FieldValue number) {
        this.symbol = symbol;
        this.number = number;
    }

}