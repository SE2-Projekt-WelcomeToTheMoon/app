package com.example.se2_projekt_app.game;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.game_interface.Clickable;
import com.example.se2_projekt_app.views.GameBoardView;

import lombok.Getter;

public class Floor implements Clickable {
    private final int y;
    // cause we only move laterally, and don't need to store Y
    @Getter
    private int nextX;
    @Getter
    private final FieldCategory category;
    private final List<Chamber> chambers;
    int boxSize = 200;
    @Getter
    private Chamber lastAccessedChamber = null;
    @Getter
    private int lastAccessedChamberIndex = -1;

    public Floor(int x, int y, FieldCategory category) {
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
        this.nextX += count * boxSize;
        chambers.add(chamber);
    }

    /**
     * Just for Testing
     *
     * @param chamber
     */
    public void addChamber(Chamber chamber) {
        this.chambers.add(chamber);
    }

    /**
     * @param canvas
     */
    public void draw(Canvas canvas, Context context) {
        for (Chamber chamber : chambers) {
            chamber.draw(canvas, context);
        }
    }

    public Chamber getChamber(int index) {
        return chambers.get(index);
    }

    public List<Chamber> getChambers() {
        return new ArrayList<>(this.chambers);
    }

    @Override
    public boolean handleClick(float x, float y, GameBoardView boardView, FieldValue fieldValue) {
        for (int i = 0; i < chambers.size(); i++) {
            Chamber chamber = chambers.get(i);
            if (chamber.handleClick(x, y, boardView, fieldValue)) {
                lastAccessedChamberIndex = i;
                lastAccessedChamber = chamber;
                return true;
            }
        }
        return false;
    }

    /**
     * Each ArrayList of ArrayLists is for a chamber, each ArrayList of Rewards are the rewards in that chamber
     *
     * @param rewardList The Rewards to be injected
     */
    public void injectRewards(List<ArrayList<Reward>> rewardList) {
        if (rewardList == null || rewardList.size() != chambers.size())
            throw new IllegalArgumentException("Rewards size must be equal to chambers size");
        int count = 0;
        for (Chamber chamber : chambers) {
            chamber.setRewards(rewardList.get(count));
            count++;
        }
    }
}
