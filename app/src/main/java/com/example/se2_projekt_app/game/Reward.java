package com.example.se2_projekt_app.game;

import com.example.se2_projekt_app.enums.RewardCategory;

import lombok.Getter;

@Getter
public class Reward {
    private final RewardCategory category;
    private int numberRockets;
    private boolean systemErrorClaimed = false;

    public Reward(RewardCategory category) {
        if (category == null) throw new IllegalArgumentException("Reward Category may not be null");
        this.category = category;

    }

    public Reward(RewardCategory category, int numberRockets) {
        if (category == null) throw new IllegalArgumentException("Reward Category may not be null");
        this.category = category;
        this.numberRockets = numberRockets;

    }

    public void claimSystemError() {
        this.systemErrorClaimed = true;
    }
}