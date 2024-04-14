package com.example.se2_projekt_app;

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
}
