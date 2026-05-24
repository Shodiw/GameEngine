package com.gameengine.pool;

import com.gameengine.exception.PoolExhaustedException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Supplier;

public class ObjectPool<T extends Poolable> {

    private final Deque<T> available = new ArrayDeque<>();
    private final int maxSize;
    private int totalCreated = 0;

    public ObjectPool(Supplier<T> factory, int initialSize, int maxSize) {
        this.maxSize = maxSize;
        for (int i = 0; i < initialSize; i++) {
            T obj = factory.get();
            obj.setActive(false);
            available.push(obj);
            totalCreated++;
        }
    }

    public T acquire() throws PoolExhaustedException {
        if (available.isEmpty()) {
            throw new PoolExhaustedException("Object pool is exhausted. Max size: " + maxSize);
        }
        T obj = available.pop();
        obj.setActive(true);
        return obj;
    }

    public void release(T obj) {
        if (obj == null) {
            return;
        }
        obj.reset();
        obj.setActive(false);
        if (available.size() < maxSize) {
            available.push(obj);
        }
    }

    public int getAvailableCount() {
        return available.size();
    }

    public int getTotalCreated() {
        return totalCreated;
    }
}
