package com.example.se2_projekt_app.game;


import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;

import lombok.Getter;

@Getter
public class CardCombination {
    private final FieldCategory currentSymbol;
    private final FieldCategory nextSymbol;
    private final FieldValue currentNumber;
    public CardCombination(FieldCategory currentSymbol, FieldCategory nextSymbol, FieldValue currentNumber){

        this.currentSymbol= currentSymbol;
        this.nextSymbol= nextSymbol;
        this.currentNumber= currentNumber;

    }

}
