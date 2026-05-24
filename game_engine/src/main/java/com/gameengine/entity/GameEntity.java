package com.gameengine.entity;

import com.gameengine.flyweight.VisualResource;

import java.util.Objects;

public abstract class GameEntity {

    private final String id;
    private double x;
    private double y;
    private int health;
    private final VisualResource visualResource;

    protected GameEntity(String id, double x, double y, int health, VisualResource visualResource) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.health = health;
        this.visualResource = visualResource;
    }

    // Template Method: базовый цикл обновления
    public final void tick() {
        update();
        render();
        cleanup();
    }

    protected abstract void update();

    protected void render() {
        visualResource.render(x, y);
    }

    protected void cleanup() {
        // default: nothing to clean up
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public VisualResource getVisualResource() {
        return visualResource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameEntity entity)) {
            return false;
        }
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{"
                + "id='" + id + '\''
                + ", x=" + x
                + ", y=" + y
                + ", health=" + health
                + '}';
    }
}
