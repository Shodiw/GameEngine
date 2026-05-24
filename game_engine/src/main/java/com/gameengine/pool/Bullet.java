package com.gameengine.pool;

import java.util.Objects;

public class Bullet implements Poolable {

    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private int damage;
    private boolean active;

    public Bullet() {
        reset();
    }

    public void init(double x, double y, double velocityX, double velocityY, int damage) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.damage = damage;
    }

    @Override
    public void reset() {
        x = 0;
        y = 0;
        velocityX = 0;
        velocityY = 0;
        damage = 0;
        active = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bullet bullet)) {
            return false;
        }
        return Double.compare(bullet.x, x) == 0
                && Double.compare(bullet.y, y) == 0
                && damage == bullet.damage
                && active == bullet.active;
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(x);
        result = 31 * result + Double.hashCode(y);
        result = 31 * result + damage;
        result = 31 * result + (active ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bullet{"
                + "x=" + x
                + ", y=" + y
                + ", velocityX=" + velocityX
                + ", velocityY=" + velocityY
                + ", damage=" + damage
                + ", active=" + active
                + '}';
    }
}
