package com.example.se2_projekt_app.game;


import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardCombination {
    private final FieldCategory currentSymbol;
    private final FieldCategory nextSymbol;
    private final FieldValue currentNumber;

    @JsonCreator
    public CardCombination(
            @JsonProperty("currentSymbol") FieldCategory currentSymbol,
            @JsonProperty("nextSymbol") FieldCategory nextSymbol,
            @JsonProperty("currentNumber") FieldValue currentNumber) {
        this.currentSymbol = currentSymbol;
        this.nextSymbol = nextSymbol;
        this.currentNumber = currentNumber;
    }

}
