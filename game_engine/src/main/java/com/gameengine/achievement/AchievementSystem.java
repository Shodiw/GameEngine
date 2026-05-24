package com.gameengine.achievement;

import com.gameengine.event.GameEvent;
import com.gameengine.event.GameEventListener;

import java.util.ArrayList;
import java.util.List;

public class AchievementSystem implements GameEventListener {

    private final List<String> unlockedAchievements = new ArrayList<>();
    private int enemiesKilled = 0;
    private int levelsCompleted = 0;

    @Override
    public void onEvent(GameEvent event, Object data) {
        switch (event) {
            case ENEMY_KILLED -> handleEnemyKilled();
            case LEVEL_COMPLETED -> handleLevelCompleted();
            default -> { /* no achievement for other events */ }
        }
    }

    private void handleEnemyKilled() {
        enemiesKilled++;
        if (enemiesKilled == 1) {
            unlockAchievement("First Blood");
        }
        if (enemiesKilled == 10) {
            unlockAchievement("Killing Spree");
        }
        if (enemiesKilled == 100) {
            unlockAchievement("Unstoppable");
        }
    }

    private void handleLevelCompleted() {
        levelsCompleted++;
        if (levelsCompleted == 1) {
            unlockAchievement("Level Cleared");
        }
        if (levelsCompleted == 5) {
            unlockAchievement("Veteran");
        }
    }

    private void unlockAchievement(String name) {
        if (!unlockedAchievements.contains(name)) {
            unlockedAchievements.add(name);
            System.out.println("[Achievement Unlocked] " + name);
        }
    }

    public List<String> getUnlockedAchievements() {
        return new ArrayList<>(unlockedAchievements);
    }

    public int getEnemiesKilled() {
        return enemiesKilled;
    }
}
