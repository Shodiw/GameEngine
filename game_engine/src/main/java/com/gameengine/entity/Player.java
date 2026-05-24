package com.gameengine.entity;

import com.gameengine.flyweight.VisualResource;

public class Player extends GameEntity {

    private int score;
    private int level;

    public Player(String id, double x, double y, int health, VisualResource visualResource) {
        super(id, x, y, health, visualResource);
        this.score = 0;
        this.level = 1;
    }

    @Override
    protected void update() {
        System.out.printf("[Player] %s updating at (%.1f, %.1f) | Score: %d | Level: %d%n",
                getId(), getX(), getY(), score, level);
    }

    public void addScore(int points) {
        this.score += points;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
