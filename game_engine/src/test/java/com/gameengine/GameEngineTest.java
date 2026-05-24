package com.gameengine;

import com.gameengine.ai.AggressiveAi;
import com.gameengine.entity.Enemy;
import com.gameengine.entity.Player;
import com.gameengine.flyweight.VisualResourceFactory;
import com.gameengine.system.GameEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    private GameEngine engine;
    private Player player;

    @BeforeEach
    void setUp() {
        VisualResourceFactory.clearCache();
        engine = new GameEngine();
        player = new Player("player_1", 0, 0, 500,
                VisualResourceFactory.getResource("tx", "md", "sd"));
        engine.setPlayer(player);
    }

    @Test
    void killEnemyAddsScoreToPlayer() {
        Enemy enemy = new Enemy("e1", 0, 0, 100,
                VisualResourceFactory.getResource("tx", "md", "sd"),
                new AggressiveAi(), 75);
        engine.addEnemy(enemy);
        engine.killEnemy("e1");
        assertEquals(75, player.getScore());
    }

    @Test
    void killEnemyTriggersAchievement() {
        Enemy enemy = new Enemy("e1", 0, 0, 100,
                VisualResourceFactory.getResource("tx", "md", "sd"),
                new AggressiveAi(), 50);
        engine.addEnemy(enemy);
        engine.killEnemy("e1");
        assertTrue(engine.getAchievementSystem().getUnlockedAchievements().contains("First Blood"));
    }

    @Test
    void completeLevelIncrementsPlayerLevel() {
        engine.completeLevel();
        assertEquals(2, player.getLevel());
    }

    @Test
    void tickRemovesDeadEnemies() {
        Enemy enemy = new Enemy("e1", 0, 0, 100,
                VisualResourceFactory.getResource("tx", "md", "sd"),
                new AggressiveAi(), 50);
        engine.addEnemy(enemy);
        engine.killEnemy("e1");
        engine.tick();
        assertEquals(0, engine.getEnemyCount());
    }
}
