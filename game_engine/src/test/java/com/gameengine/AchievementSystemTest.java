package com.gameengine;

import com.gameengine.achievement.AchievementSystem;
import com.gameengine.event.GameEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AchievementSystemTest {

    private AchievementSystem system;

    @BeforeEach
    void setUp() {
        system = new AchievementSystem();
    }

    @Test
    void firstKillShouldUnlockFirstBlood() {
        system.onEvent(GameEvent.ENEMY_KILLED, null);
        assertTrue(system.getUnlockedAchievements().contains("First Blood"));
    }

    @Test
    void tenKillsShouldUnlockKillingSpree() {
        for (int i = 0; i < 10; i++) {
            system.onEvent(GameEvent.ENEMY_KILLED, null);
        }
        assertTrue(system.getUnlockedAchievements().contains("Killing Spree"));
    }

    @Test
    void levelCompleteShouldUnlockLevelCleared() {
        system.onEvent(GameEvent.LEVEL_COMPLETED, null);
        assertTrue(system.getUnlockedAchievements().contains("Level Cleared"));
    }

    @Test
    void achievementsAreNotDuplicated() {
        system.onEvent(GameEvent.ENEMY_KILLED, null);
        system.onEvent(GameEvent.ENEMY_KILLED, null);
        long count = system.getUnlockedAchievements().stream()
                .filter("First Blood"::equals).count();
        assertEquals(1, count);
    }
}
