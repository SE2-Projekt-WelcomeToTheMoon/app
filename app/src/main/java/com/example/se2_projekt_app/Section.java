package com.example.se2_projekt_app;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class Section {
    private List<GameBox> boxes;
    private int left;
    private int top;

    public Section(int left, int top) {
        this.boxes = new ArrayList<>();
        this.left = left;
        this.top = top;
    }

    public void addBox(GameBox box) {
        boxes.add(box);
    }

    public void draw(Canvas canvas) {
        for (GameBox box : boxes) {
            box.drawTranslated(canvas, left, top);
        }
    }

    public void handleClick(float x, float y, GameBoardView boardView) {
        for (GameBox box : boxes) {
            if (box.contains(x - left, y - top)) {
                box.setColor(Color.GREEN);
                box.setNumber(box.getNumber()+1);
                boardView.drawGameBoard();
                break;
            }
        }
    }
}
