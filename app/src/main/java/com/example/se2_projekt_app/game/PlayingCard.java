package com.example.se2_projekt_app.game;

import com.example.se2_projekt_app.enums.FieldCategory;

public class PlayingCard{

    private FieldCategory symbol;
    private int number;

    public PlayingCard(FieldCategory symbol, int number) {
        this.symbol = symbol;
        this.number = number;
    }

    public FieldCategory getSymbol() {
        return symbol;
    }

    public int getNumber() {
        return number;
    }
}