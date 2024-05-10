package com.example.se2_projekt_app.enums;

import android.graphics.Color;

public enum FieldCategory {
    ROBOT(Color.DKGRAY),
    WATER(Color.BLUE),
    PLANT(Color.GREEN),
    ENERGY(Color.MAGENTA),
    SPACESUIT(Color.YELLOW),
    PLANNING(Color.RED),
    WILDCARD(Color.GRAY);

    private final int color;

    FieldCategory(int color) {
        this.color = color;
    }
    public int getColor() {
        return color;
    }
}
