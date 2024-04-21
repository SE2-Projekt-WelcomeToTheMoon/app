package com.example.se2_projekt_app.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.RewardCategory;
import com.example.se2_projekt_app.game_interface.Clickable;
import com.example.se2_projekt_app.views.GameBoardView;

public class Floor implements Clickable {
    int x;
    int y;
    int nextX;
    private FieldCategory category;
    private List<Chamber> chambers;

    int boxSize = 200;

    public Floor(int x, int y, FieldCategory category) {
        this.x = x;
        this.y = y;
        this.nextX = x;
        this.category = category;
        this.chambers = new ArrayList<>();
    }

    public void addChamber(int count, List<RewardCategory> rewards) {
        Chamber chamber = new Chamber(nextX, y, count, rewards, category);
        // shift to the right for the next chamber why doesnt it work with 200????
        this.nextX += count * boxSize - 100;
        chambers.add(chamber);
    }

    private void initOutlinePaint() {
        Paint outlinePaint;
        outlinePaint = new Paint();
        outlinePaint.setColor(Color.BLACK);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(10);
    }

    /**
     *
     * TODO draw the outline of the floor
     * int totalWidth = nextX;
     * canvas.drawRect(x, y, nextX, y + 200, outlinePaint);
     *
     * @param canvas
     */
    public void draw(Canvas canvas) {
        initOutlinePaint();

        for (Chamber chamber : chambers) {
            chamber.draw(canvas);
        }
    }


    public List<Chamber> getChambers() {
        return chambers;
    }

    @Override
    public boolean handleClick(float x, float y, GameBoardView boardView) {
        float relX = x - this.x;
        float relY = y - this.y;

        for (Chamber chamber : chambers) {
            Log.d("Floor", "Checking chamber at " + x + ", " + y );
            if (chamber.handleClick(relX, relY, boardView)) {
                return true;
            }
        }
        return false;
    }
}
