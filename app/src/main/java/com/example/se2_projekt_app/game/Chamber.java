package com.example.se2_projekt_app.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.game_interface.Clickable;
import com.example.se2_projekt_app.views.GameBoardView;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Represents a section of the game board that contains multiple GameBoxes.
 */
public class Chamber implements Clickable {
    private final List<Field> fields;

    private final int x;

    private final int y;

    int boxSize = 200;

    /**
     * Constructs a Section with a specified origin.
     *
     * @param x The x-coordinate of the top-left corner of the section.
     * @param y The y-coordinate of the top-left corner of the section.
     */
    public Chamber(int x, int y, int count, FieldCategory category) {
        this.fields = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            fields.add(new Field(x + i * boxSize, y, boxSize, category.getColor(), FieldValue.NONE));
        }
        this.x = x;
        this.y = y;
    }

    private void initOutlinePaint() {
        Paint outlinePaint;
        outlinePaint = new Paint();
        outlinePaint.setColor(Color.BLACK);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(10);
    }

    /**
     * Draws all GameBoxes in the section, translated to the section's position.
     *
     * @param canvas The canvas on which to draw the boxes.
     */
    public void draw(Canvas canvas) {
        initOutlinePaint();

        for (Field box : fields) {
            box.draw(canvas, x, y);
        }
    }

    public Field getField(int index) {
        return fields.get(index);
    }

    /**
     * For Testing purposes
     *
     * @return
     */
    public int getSize() {
        return fields.size();
    }

    /**
     * Handles click events within the section, checks if a click is within any GameBox,
     * and updates the box color and number if clicked.
     *
     * @param x         The x-coordinate of the click relative to the section's parent container.
     * @param y         The y-coordinate of the click relative to the section's parent container.
     * @param boardView The view that needs to be redrawn after handling the click.
     */
    @Override
    public boolean handleClick(float x, float y, GameBoardView boardView) {
        float relX = x - this.x;
        float relY = y - this.y;

        Log.d("Chamber", "Checking chamber at " + x + ", " + y);
        for (Field field : fields) {

            if (field.handleClick(relX, relY, boardView)) {
                return true;
            }
        }
        return false;
    }
}