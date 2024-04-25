package com.example.se2_projekt_app.game;

import com.example.se2_projekt_app.enums.Element;
import com.example.se2_projekt_app.enums.FieldValue;

import lombok.Getter;

public class PlayingCard{

    @Getter
    private Element symbol;
    @Getter
    private FieldValue number;

    public PlayingCard(Element symbol, FieldValue number) {
        this.symbol = symbol;
        this.number = number;
    }

}