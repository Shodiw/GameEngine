package com.gameengine;

import com.gameengine.entity.Enemy;
import com.gameengine.flyweight.VisualResourceFactory;
import com.gameengine.reader.EntityReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntityReaderTest {

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        VisualResourceFactory.clearCache();
    }

    @Test
    void validLinesAreLoaded() throws IOException {
        Path file = tempDir.resolve("enemies.txt");
        Files.writeString(file,
                "enemy_001,10.0,5.0,100,AGGRESSIVE,50,tex,mod,snd\n"
                        + "enemy_002,0.0,0.0,200,PATROL,80,tex,mod,snd\n");
        List<Enemy> enemies = EntityReader.readEnemies(file);
        assertEquals(2, enemies.size());
    }

    @Test
    void invalidLinesAreSkipped() throws IOException {
        Path file = tempDir.resolve("mixed.txt");
        Files.writeString(file,
                "enemy_001,10.0,5.0,100,AGGRESSIVE,50,tex,mod,snd\n"
                        + "INVALID\n"
                        + "enemy_002,1.0,1.0,abc,PATROL,10,tx,md,sd\n"
                        + "enemy_003,2.0,2.0,150,DEFENSIVE,30,tex2,mod2,snd2\n");
        List<Enemy> enemies = EntityReader.readEnemies(file);
        assertEquals(2, enemies.size());
    }

    @Test
    void commentsAndBlankLinesAreIgnored() throws IOException {
        Path file = tempDir.resolve("comments.txt");
        Files.writeString(file,
                "# This is a comment\n"
                        + "\n"
                        + "enemy_001,1.0,1.0,100,PATROL,20,tex,mod,snd\n");
        List<Enemy> enemies = EntityReader.readEnemies(file);
        assertEquals(1, enemies.size());
    }

    @Test
    void flyweightCacheIsSharedForSameResources() throws IOException {
        Path file = tempDir.resolve("flyweight.txt");
        Files.writeString(file,
                "enemy_001,1.0,1.0,100,PATROL,10,tex,mod,snd\n"
                        + "enemy_002,2.0,2.0,100,PATROL,10,tex,mod,snd\n"
                        + "enemy_003,3.0,3.0,100,AGGRESSIVE,10,OTHER,mod2,snd2\n");
        EntityReader.readEnemies(file);
        assertEquals(2, VisualResourceFactory.getCacheSize());
    }
}
