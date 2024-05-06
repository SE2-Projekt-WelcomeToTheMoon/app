package com.example.se2_projekt_app.game;


import com.example.se2_projekt_app.enums.Element;
import com.example.se2_projekt_app.enums.FieldValue;

import lombok.Getter;

@Getter
public class CardCombination {
    private final Element currentSymbol;
    private final Element nextSymbol;
    private final FieldValue currentNumber;
    public CardCombination(Element currentSymbol, Element nextSymbol, FieldValue currentNumber){

        this.currentSymbol= currentSymbol;
        this.nextSymbol= nextSymbol;
        this.currentNumber= currentNumber;

    }

}
