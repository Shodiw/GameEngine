package com.gameengine.system;

import com.gameengine.achievement.AchievementSystem;
import com.gameengine.entity.Enemy;
import com.gameengine.entity.Player;
import com.gameengine.event.EventBus;
import com.gameengine.event.GameEvent;
import com.gameengine.exception.PoolExhaustedException;
import com.gameengine.pool.Bullet;
import com.gameengine.pool.ObjectPool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameEngine {

    private final EventBus eventBus;
    private final AchievementSystem achievementSystem;
    private final ObjectPool<Bullet> bulletPool;
    private final List<Enemy> enemies = new ArrayList<>();
    private Player player;

    public GameEngine() {
        this.eventBus = new EventBus();
        this.achievementSystem = new AchievementSystem();
        this.bulletPool = new ObjectPool<>(Bullet::new, 10, 50);

        eventBus.subscribe(GameEvent.ENEMY_KILLED, achievementSystem);
        eventBus.subscribe(GameEvent.LEVEL_COMPLETED, achievementSystem);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void fireBullet(double fromX, double fromY, double vx, double vy, int damage) {
        try {
            Bullet bullet = bulletPool.acquire();
            bullet.init(fromX, fromY, vx, vy, damage);
            System.out.println("[Engine] Bullet fired: " + bullet);
            eventBus.publish(GameEvent.BULLET_FIRED, bullet);
            // Immediately return to pool for demo purposes
            bulletPool.release(bullet);
        } catch (PoolExhaustedException e) {
            System.err.println("[Engine] Cannot fire bullet: " + e.getMessage());
        }
    }

    public void killEnemy(String enemyId) {
        enemies.stream()
                .filter(e -> e.getId().equals(enemyId))
                .findFirst()
                .ifPresent(enemy -> {
                    enemy.setHealth(0);
                    if (player != null) {
                        player.addScore(enemy.getScoreValue());
                    }
                    eventBus.publish(GameEvent.ENEMY_KILLED, enemy);
                    System.out.println("[Engine] Enemy killed: " + enemyId);
                });
    }

    public void completeLevel() {
        eventBus.publish(GameEvent.LEVEL_COMPLETED, null);
        if (player != null) {
            player.setLevel(player.getLevel() + 1);
        }
        System.out.println("[Engine] Level completed!");
    }

    public void tick() {
        if (player != null) {
            player.tick();
        }
        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            if (enemy.isAlive()) {
                enemy.tick();
            } else {
                it.remove();
            }
        }
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public AchievementSystem getAchievementSystem() {
        return achievementSystem;
    }

    public int getEnemyCount() {
        return enemies.size();
    }

    public ObjectPool<Bullet> getBulletPool() {
        return bulletPool;
    }
}
