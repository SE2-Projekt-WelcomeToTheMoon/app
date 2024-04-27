package com.example.se2_projekt_app.enums;

import android.graphics.Color;

public enum Element {
    ROBOT(Color.DKGRAY),
    WATER(Color.BLUE),
    PLANT(Color.GREEN),
    ENERGY(Color.MAGENTA),
    SPACESUIT(Color.YELLOW),
    PLANNING(Color.RED),
    WILDCARD(Color.GRAY);

    private final int color;

    Element(int color) {
        this.color = color;
    }
    public int getColor() {
        return color;
    }
    /***
     * The enum on the Serverside has german Names while the client side has english names, so this takes
     * the String with the german name and finds the corresponding element
     * @param element Element name in german from serverside
     * @return corresponding element
     */
    public static Element getSymbolAndTranslate(String element) {

        switch(element) {
            case "ROBOTER":
                return Element.ROBOT;
            case "WASSER":
                return Element.WATER;
            case "PFLANZE":
                return Element.PLANT;
            case "ENERGIE":
                return Element.ENERGY;
            case "RAUMANZUG":
                return Element.SPACESUIT;
            case "PLANNUNG":
                return Element.PLANNING;
            case "ANYTHING":
                return Element.WILDCARD;
            default:
                throw new IllegalArgumentException("Not part of recognized symbols");
        }
    }
}
