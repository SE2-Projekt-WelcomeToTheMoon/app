package com.example.se2_projekt_app.game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.se2_projekt_app.enums.RewardCategory;

class RewardTest {
    @Test
    void testRewardConstruction() {
        Reward reward = new Reward(RewardCategory.ROCKET);
        assertEquals(RewardCategory.ROCKET, reward.getCategory());
        assertFalse(reward.isSystemErrorClaimed());
        assertEquals(0, reward.getNumberRockets());
    }

    @Test
    void testRewardConstructionWithNumberRockets() {
        Reward reward = new Reward(RewardCategory.ROCKET, 5);
        assertEquals(RewardCategory.ROCKET, reward.getCategory());
        assertFalse(reward.isSystemErrorClaimed());
        assertEquals(5, reward.getNumberRockets());
    }



    @Test
    void testRewardConstructionWithInvalidCategory() {
        assertThrows(IllegalArgumentException.class,() -> new Reward(null));
    }
    @Test
    void testClaimSystemError() {
        Reward reward = new Reward(RewardCategory.SYSTEMERROR); // Any valid category
        assertFalse(reward.isSystemErrorClaimed());
        reward.claimSystemError();
        assertTrue(reward.isSystemErrorClaimed());
    }

    @Test
    void testRewardCategoryEnumeration() {
        for (RewardCategory category : RewardCategory.values()) {
            Reward reward = new Reward(category);
            assertEquals(category, reward.getCategory());
        }
    }
}

