package com.gameengine;

import com.gameengine.exception.PoolExhaustedException;
import com.gameengine.pool.Bullet;
import com.gameengine.pool.ObjectPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectPoolTest {

    private ObjectPool<Bullet> pool;

    @BeforeEach
    void setUp() {
        pool = new ObjectPool<>(Bullet::new, 5, 10);
    }

    @Test
    void acquireShouldReturnActiveBullet() throws PoolExhaustedException {
        Bullet bullet = pool.acquire();
        assertTrue(bullet.isActive());
    }

    @Test
    void releaseShouldResetAndReturnToPool() throws PoolExhaustedException {
        int before = pool.getAvailableCount();
        Bullet bullet = pool.acquire();
        bullet.init(1, 2, 3, 4, 10);
        pool.release(bullet);
        assertEquals(before, pool.getAvailableCount());
        assertFalse(bullet.isActive());
        assertEquals(0, bullet.getDamage());
    }

    @Test
    void exhaustedPoolShouldThrow() {
        assertThrows(PoolExhaustedException.class, () -> {
            for (int i = 0; i < 100; i++) {
                pool.acquire();
            }
        });
    }
}
