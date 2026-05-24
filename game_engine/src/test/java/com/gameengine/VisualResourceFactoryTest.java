package com.gameengine;

import com.gameengine.flyweight.VisualResource;
import com.gameengine.flyweight.VisualResourceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisualResourceFactoryTest {

    @BeforeEach
    void setUp() {
        VisualResourceFactory.clearCache();
    }

    @Test
    void sameKeyReturnsSameInstance() {
        VisualResource r1 = VisualResourceFactory.getResource("tx", "md", "sd");
        VisualResource r2 = VisualResourceFactory.getResource("tx", "md", "sd");
        assertSame(r1, r2);
    }

    @Test
    void differentKeyReturnsNewInstance() {
        VisualResource r1 = VisualResourceFactory.getResource("tx1", "md1", "sd1");
        VisualResource r2 = VisualResourceFactory.getResource("tx2", "md2", "sd2");
        assertNotSame(r1, r2);
    }

    @Test
    void cacheSizeTracksUniqueResources() {
        VisualResourceFactory.getResource("tx", "md", "sd");
        VisualResourceFactory.getResource("tx", "md", "sd");
        VisualResourceFactory.getResource("tx2", "md2", "sd2");
        assertEquals(2, VisualResourceFactory.getCacheSize());
    }
}
