package com.example.se2_projekt_app.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.game_interface.Clickable;
import com.example.se2_projekt_app.views.GameBoardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a section of the game board that contains multiple GameBoxes.
 */
public class Chamber implements Clickable {
    private final List<Field> fields;
    private final int x;
    private final int y;
    int boxSize = 200;
    private int lastAccessedField = 0;

    /**
     * Constructs a Section with a specified origin.
     *
     * @param x The x-coordinate of the top-left corner of the section.
     * @param y The y-coordinate of the top-left corner of the section.
     */
    public Chamber(int x, int y, int count, FieldCategory category) {
        this.fields = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            fields.add(new Field(x + (i * boxSize), y, boxSize, category, FieldValue.NONE));
        }
        this.x = x;
        this.y = y;
    }

    /**
     * For Testing purposes
     */
    public void addField(Field field) {
        this.fields.add(field);
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

        for (Field field : fields) {
            field.draw(canvas);
        }
    }

    public Field getField(int index) {
        return fields.get(index);
    }

    public List<Field> getFields() {
        return new ArrayList<>(fields);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
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
    public boolean handleClick(float x, float y, GameBoardView boardView, FieldValue fieldValue) {
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            if (field.handleClick(x, y, boardView, fieldValue)) {
                lastAccessedField = i;
                return true;
            }
        }
        return false;
    }

    public int getLastAccessedField() {
        return lastAccessedField;
    }
}