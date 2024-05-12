package com.example.se2_projekt_app.enums;

import android.graphics.Color;

import lombok.Getter;

@Getter
public enum FieldCategory {
    ROBOT(Color.DKGRAY),
    WATER(Color.BLUE),
    PLANT(Color.GREEN),
    ENERGY(Color.MAGENTA),
    SPACESUIT(Color.YELLOW),
    PLANNING(Color.RED),
    WILDCARD(Color.GRAY);

    private final int color;

    public int getColor(){
        return this.color;
    }

    FieldCategory(int color) {
        this.color = color;
    }

    /***
     * The enum on the Serverside has german Names while the client side has english names, so this takes
     * the String with the german name and finds the corresponding element
     * @param element Element name in german from serverside
     * @return corresponding element
     */
    public static FieldCategory getSymbolAndTranslate(String element) {

        switch(element) {
            case "ROBOTER":
                return FieldCategory.ROBOT;
            case "WASSER":
                return FieldCategory.WATER;
            case "PFLANZE":
                return FieldCategory.PLANT;
            case "ENERGIE":
                return FieldCategory.ENERGY;
            case "RAUMANZUG":
                return FieldCategory.SPACESUIT;
            case "PLANNUNG":
                return FieldCategory.PLANNING;
            case "ANYTHING":
                return FieldCategory.WILDCARD;
            default:
                throw new IllegalArgumentException("Not part of recognized symbols");
        }
    }
}
