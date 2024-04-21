package com.example.se2_projekt_app.game;

import com.example.se2_projekt_app.enums.RewardCategory;
public class Reward {
    private RewardCategory category;
    private int quantity;

    public Reward(RewardCategory category, int quantity) {
        this.category = category;
        this.quantity = quantity;
    }

    public Reward(RewardCategory category) {
        this(category, 1);
    }

    public RewardCategory getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }
}
