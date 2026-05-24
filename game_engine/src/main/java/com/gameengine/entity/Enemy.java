package com.gameengine.entity;

import com.gameengine.ai.AiStrategy;
import com.gameengine.flyweight.VisualResource;

public class Enemy extends GameEntity {

    private AiStrategy aiStrategy;
    private int scoreValue;

    public Enemy(String id, double x, double y, int health, VisualResource visualResource,
                 AiStrategy aiStrategy, int scoreValue) {
        super(id, x, y, health, visualResource);
        this.aiStrategy = aiStrategy;
        this.scoreValue = scoreValue;
    }

    @Override
    protected void update() {
        aiStrategy.execute(getId(), getX(), getY());
    }

    @Override
    protected void cleanup() {
        if (!isAlive()) {
            System.out.println("[Cleanup] Removing dead enemy: " + getId());
        }
    }

    public void setAiStrategy(AiStrategy aiStrategy) {
        this.aiStrategy = aiStrategy;
    }

    public AiStrategy getAiStrategy() {
        return aiStrategy;
    }

    public int getScoreValue() {
        return scoreValue;
    }
}
