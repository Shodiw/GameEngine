package com.gameengine;

import com.gameengine.entity.Enemy;
import com.gameengine.entity.Player;
import com.gameengine.flyweight.VisualResourceFactory;
import com.gameengine.reader.EntityReader;
import com.gameengine.system.GameEngine;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Game Engine Demo ===\n");

        // Setup engine
        GameEngine engine = new GameEngine();

        // Create player using Flyweight resource
        Player player = new Player(
                "player_1",
                0.0, 0.0,
                500,
                VisualResourceFactory.getResource("texture_hero", "model_hero", "sound_hero")
        );
        engine.setPlayer(player);

        // Load enemies from file (Java 8+ Files.lines)
        Path enemiesFile = Paths.get("src/main/resources/enemies.txt");
        try {
            List<Enemy> enemies = EntityReader.readEnemies(enemiesFile);
            System.out.println("\n[Main] Loaded " + enemies.size() + " valid enemies from file.");
            System.out.println("[Main] Flyweight cache size (unique visual resources): "
                    + VisualResourceFactory.getCacheSize() + "\n");
            enemies.forEach(engine::addEnemy);
        } catch (IOException e) {
            System.err.println("[Main] Failed to read enemies file: " + e.getMessage());
        }

        // Run one game tick (Template Method: update → render → cleanup)
        System.out.println("--- TICK 1 ---");
        engine.tick();

        // Fire bullets using Object Pool
        System.out.println("\n--- FIRING BULLETS ---");
        engine.fireBullet(0, 0, 1.0, 0.5, 25);
        engine.fireBullet(0, 0, -1.0, 0.5, 25);
        System.out.println("Bullets available in pool after fire+release: "
                + engine.getBulletPool().getAvailableCount());

        // Kill enemies to trigger Observer → AchievementSystem
        System.out.println("\n--- KILLING ENEMIES ---");
        engine.killEnemy("enemy_001");
        engine.killEnemy("enemy_002");
        engine.killEnemy("enemy_003");

        System.out.println("\nPlayer score: " + player.getScore());
        System.out.println("Achievements: " + engine.getAchievementSystem().getUnlockedAchievements());

        // Complete level
        System.out.println("\n--- LEVEL COMPLETE ---");
        engine.completeLevel();
        System.out.println("Player level: " + player.getLevel());
        System.out.println("Achievements: " + engine.getAchievementSystem().getUnlockedAchievements());
    }
}
