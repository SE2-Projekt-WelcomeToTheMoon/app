package com.example.se2_projekt_app.game;

import com.example.se2_projekt_app.enums.Element;
import com.example.se2_projekt_app.enums.FieldValue;

public class PlayingCard{

    private Element symbol;
    private FieldValue number;

    public PlayingCard(Element symbol, FieldValue number) {
        this.symbol = symbol;
        this.number = number;
    }

    public Element getSymbol() {
        return symbol;
    }

    public FieldValue getNumber() {
        return number;
    }
}