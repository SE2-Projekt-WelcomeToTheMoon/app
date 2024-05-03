package com.example.se2_projekt_app.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.RewardCategory;
import com.example.se2_projekt_app.game_interface.Clickable;
import com.example.se2_projekt_app.views.GameBoardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a section of the game board that contains multiple GameBoxes.
 */
public class Chamber implements Clickable {
    private final List<Field> fields;
    private final List<RewardCategory> rewards;

    private int x;
    private int y;

    int boxSize = 200;

    /**
     * Constructs a Section with a specified origin.
     *
     * @param x       The x-coordinate of the top-left corner of the section.
     * @param y       The y-coordinate of the top-left corner of the section.
     * @param rewards
     */
    public Chamber(int x, int y, int count, List<RewardCategory> rewards, FieldCategory category) {
        this.rewards = rewards;
        this.fields = new ArrayList<>();
        // remove when rewards are used
        doSomething();

        for (int i = 0; i < count; i++) {
            fields.add(new Field(x + i * boxSize, y, boxSize, category.getColor(), 0));
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
     * TODO reimplement this but working
     * int totalWidth = boxes.size() * this.boxSize;
     * canvas.drawRect(x, y, x + totalWidth, y + 200, outlinePaint);  // Assumes each box is 200 pixels high
     *
     *
     * @param canvas The canvas on which to draw the boxes.
     */
    public void draw(Canvas canvas) {
        initOutlinePaint();


        for (Field box : fields) {
            box.draw(canvas, x, y);
        }
    }

    /**
     * PLACEHOLDER
     * remove when rewards are used, just here so that sonarcloud doesnt complain
     */
    public void doSomething() {
        for (RewardCategory reward : rewards) {
            Log.d("Chamber", "Reward: " + reward);
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

    public Field getField(int index) {
        return fields.get(index);
    }
}