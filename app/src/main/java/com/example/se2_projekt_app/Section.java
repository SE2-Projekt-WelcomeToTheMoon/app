package com.example.se2_projekt_app;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a section of the game board that contains multiple GameBoxes.
 */
public class Section {
    private final List<GameBox> boxes;
    private final int x;
    private final int y;

    /**
     * Constructs a Section with a specified origin.
     *
     * @param x The x-coordinate of the top-left corner of the section.
     * @param y The y-coordinate of the top-left corner of the section.
     */
    public Section(int x, int y) {
        this.boxes = new ArrayList<>();
        this.x = x;
        this.y = y;
    }

    /**
     * Adds a GameBox to the section.
     *
     * @param box The GameBox to add.
     */
    public void addBox(GameBox box) {
        boxes.add(box);
    }

    /**
     * Draws all GameBoxes in the section, translated to the section's position.
     *
     * @param canvas The canvas on which to draw the boxes.
     */
    public void draw(Canvas canvas) {
        for (GameBox box : boxes) {
            box.drawTranslated(canvas, x, y);
        }
    }

    /**
     * Handles click events within the section, checks if a click is within any GameBox,
     * and updates the box color and number if clicked.
     *
     * @param x The x-coordinate of the click relative to the section's parent container.
     * @param y The y-coordinate of the click relative to the section's parent container.
     * @param boardView The view that needs to be redrawn after handling the click.
     */
    public void handleClick(float x, float y, GameBoardView boardView) {
        float relX = x - this.x;
        float relY = y - this.y;

        for (GameBox box : boxes) {
            if (box.contains(relX, relY)) {
                box.setColor(Color.GREEN);
                box.setNumber(box.getNumber() + 1);
                boardView.drawGameBoard();
                break;
            }
        }
    }
}