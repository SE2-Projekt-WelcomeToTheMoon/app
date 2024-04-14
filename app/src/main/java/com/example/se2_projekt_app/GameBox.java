package com.example.se2_projekt_app;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Represents a drawable box used in a game that can display a number.
 */
public class GameBox {
    private final int x;
    private final int y;
    private final int size;
    private int number;
    private final Paint paint;
    private final Paint textPaint;

    /**
     * Constructs a GameBox with specified location, size, color, and number.
     *
     * @param x The x-coordinate of the top-left corner.
     * @param y The y-coordinate of the top-left corner.
     * @param size The size of each side of the square box.
     * @param color The fill color of the box.
     * @param number The number to display in the box.
     */
    public GameBox(int x, int y, int size, int color, int number) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.number = number;

        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(0xFFFFFFFF);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * Draws the box and its number at its current location.
     *
     * @param canvas The canvas on which to draw the box.
     */
    public void draw(Canvas canvas) {
        canvas.drawRect(x, y, x + size, y + size, paint);
        drawNumber(canvas, x, y);
    }

    /**
     * Checks if the specified coordinates are inside the bounds of this box.
     *
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return true if the coordinates are within the box, false otherwise.
     */
    public boolean contains(float x, float y) {
        return x >= this.x && x < this.x + size && y >= this.y && y < this.y + size;
    }

    /**
     * Draws the box and its number translated by the specified offsets.
     *
     * @param canvas The canvas on which to draw the translated box.
     * @param offsetX The horizontal offset.
     * @param offsetY The vertical offset.
     */
    public void drawTranslated(Canvas canvas, int offsetX, int offsetY) {
        canvas.drawRect(x + offsetX, y + offsetY, x + offsetX + size, y + offsetY + size, paint);
        drawNumber(canvas, x + offsetX, y + offsetY);
    }

    /**
     * Draws the number at the center of the given location.
     *
     * @param canvas The canvas on which to draw the number.
     * @param offsetX The horizontal offset for the x-coordinate.
     * @param offsetY The vertical offset for the y-coordinate.
     */
    private void drawNumber(Canvas canvas, int offsetX, int offsetY) {
        float centerX = offsetX + size / 2;
        float centerY = offsetY + size / 2 - ((textPaint.descent() + textPaint.ascent()) / 2);
        canvas.drawText(String.valueOf(number), centerX, centerY, textPaint);
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public int getNumber() {
        return number;
    }

    public void setColor(int color) {
        paint.setColor(color);
    }
}
