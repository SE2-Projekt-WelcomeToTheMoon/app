package com.example.se2_projekt_app.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.game_interface.Clickable;
import com.example.se2_projekt_app.views.GameBoardView;

import lombok.Getter;

public class Floor implements Clickable {
    private final int x;
    private final int y;
    // cause we only move laterally, and don't need to store Y
    private int nextX;
    private final FieldCategory category;
    private final List<Chamber> chambers;

    int boxSize = 200;

    public Floor(int x, int y, FieldCategory category) {
        this.x = x;
        this.y = y;
        this.nextX = x;
        this.category = category;
        this.chambers = new ArrayList<>();
    }

    /**
     * Adds a Chamber to the Floor
     *
     * @param count Refers to the Amount of Fields the Chamber should have
     */
    public void addChamber(int count) {
        Chamber chamber = new Chamber(nextX, y, count, category);
        // shift to the right for the next chamber why doesn't it work with 200????
        this.nextX += count * boxSize;
        chambers.add(chamber);
    }

    /**
     * Just for Testing
     * @param chamber
     */
    public void addChamber(Chamber chamber) {
        this.chambers.add(chamber);
    }

    /**
     * @param canvas
     */
    public void draw(Canvas canvas) {
        for (Chamber chamber : chambers) {
            chamber.draw(canvas);
        }
    }

    public Chamber getChamber(int index) {
        return chambers.get(index);
    }

    public List<Chamber> getChambers() {
        return new ArrayList<>(this.chambers);
    }
    public int getNextX() {
        return this.nextX;
    }

    @Override
    public boolean handleClick(float x, float y, GameBoardView boardView) {
        for (Chamber chamber : chambers) {
            if (chamber.handleClick(x, y, boardView)) {
                return true;
            }
        }
        return false;
    }
}
