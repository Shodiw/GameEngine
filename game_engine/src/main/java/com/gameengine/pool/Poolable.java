package com.gameengine.pool;

public interface Poolable {

    void reset();

    boolean isActive();

    void setActive(boolean active);
}
