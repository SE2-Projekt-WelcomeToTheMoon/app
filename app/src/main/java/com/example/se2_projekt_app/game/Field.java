package com.example.se2_projekt_app.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.game_interface.Clickable;
import com.example.se2_projekt_app.views.GameBoardView;

/**
 * Represents a drawable box used in a game that can display a number.
 */
public class Field implements Clickable {
    private final int x;
    private final int y;
    private final int size;
    private FieldValue fieldValue;
    public final Paint paint;
    private final Paint textPaint;
    private final Paint outlinePaint;

    /**
     * Constructs a GameBox with specified location, size, color, and number.
     *
     * @param x      The x-coordinate of the top-left corner.
     * @param y      The y-coordinate of the top-left corner.
     * @param size   The size of each side of the square box.
     * @param color  The fill color of the box.
     * @param fieldValue The number to display in the box.
     */
    public Field(int x, int y, int size, int color, FieldValue fieldValue) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.fieldValue = fieldValue;

        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(0xFFFFFFFF);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);

        outlinePaint = new Paint();
        outlinePaint.setColor(0xFF000000);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(5);
    }

    /**
     * Draws the box and its number translated by the specified offsets.
     *
     * @param canvas  The canvas on which to draw the translated box.
     * @param offsetX The horizontal offset.
     * @param offsetY The vertical offset.
     */
    public void draw(Canvas canvas, int offsetX, int offsetY) {
        canvas.drawRect((float) x + offsetX, (float) y + offsetY, (float) x + offsetX + size, (float) y + offsetY + size, paint);
        canvas.drawRect((float) x + offsetX, (float) y + offsetY, (float) x + offsetX + size, (float) y + offsetY + size, outlinePaint);
        drawNumber(canvas, x + offsetX, y + offsetY);
    }

    /**
     * Draws the number at the center of the given location.
     *
     * @param canvas  The canvas on which to draw the number.
     * @param offsetX The horizontal offset for the x-coordinate.
     * @param offsetY The vertical offset for the y-coordinate.
     */
    private void drawNumber(Canvas canvas, int offsetX, int offsetY) {
        float centerX = offsetX + (float) size / 2;
        float centerY = offsetY + (float) size / 2 - ((textPaint.descent() + textPaint.ascent()) / 2);
        canvas.drawText(String.valueOf(this.fieldValue.getValue()), centerX, centerY, textPaint);
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setNumber(FieldValue fieldValue) {
        this.fieldValue = fieldValue;
    }

    @Override
    public boolean handleClick(float x, float y, GameBoardView boardView) {
        Log.d("GameBox", "Checking box at " + x + ", " + y);
        // TODO change to proper logic with the CardView
        if (isPointInsideBox(x, y)) {
            Log.d("GameBox", "Box clicked at " + this.x + ", " + this.y);
            this.fieldValue = FieldValue.FIFTEEN;
            this.paint.setColor(Color.GREEN);
            boardView.drawGameBoard();
            return true;
        }
        return false;
    }

    private boolean isPointInsideBox(float x, float y) {
        return x >= this.x && x < this.x + size && y >= this.y && y < this.y + size;
    }
}