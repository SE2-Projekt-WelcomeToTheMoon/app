package com.example.se2_projekt_app;

import android.graphics.Canvas;
import android.graphics.Paint;

public class GameBox {
    private int x;
    private int y;
    private int size;
    private Paint paint;
    private Paint textPaint;
    private int number;

    public GameBox(int left, int top, int size, int color, int number) {
        this.x = left;
        this.y = top;
        this.size = size;
        this.number = number;

        this.paint = new Paint();
        this.paint.setColor(color);
        this.paint.setStyle(Paint.Style.FILL);

        this.textPaint = new Paint();
        this.textPaint.setColor(0xFFFFFFFF);
        this.textPaint.setTextSize(40);
        this.textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(x, y, x + size, y + size, paint);

        float centerX = x + size / 2;
        float centerY = y + size / 2 - ((textPaint.descent() + textPaint.ascent()) / 2);
        canvas.drawText(String.valueOf(number), centerX, centerY, textPaint);
    }

    public boolean contains(float x, float y) {
        return x >= this.x && x < this.x + size && y >= this.y && y < this.y + size;
    }

    public void drawTranslated(Canvas canvas, int offsetX, int offsetY) {
        canvas.drawRect(x + offsetX, y + offsetY, x + offsetX + size, y + offsetY + size, paint);
        float centerX = x + offsetX + size / 2;
        float centerY = y + offsetY + size / 2 - ((textPaint.descent() + textPaint.ascent()) / 2);
        canvas.drawText(String.valueOf(number), centerX, centerY, textPaint);
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public int getNumber() {
        return number;
    }

    public void setColor(int color) {
        this.paint.setColor(color);
    }
}
