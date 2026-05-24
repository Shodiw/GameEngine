package com.gameengine.reader;

import com.gameengine.ai.AggressiveAi;
import com.gameengine.ai.AiStrategy;
import com.gameengine.ai.DefensiveAi;
import com.gameengine.ai.PatrolAi;
import com.gameengine.entity.Enemy;
import com.gameengine.exception.EntityParseException;
import com.gameengine.flyweight.VisualResource;
import com.gameengine.flyweight.VisualResourceFactory;
import com.gameengine.validator.EntityValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Reads enemy entities from a text file.
 * Format per line: id,x,y,health,aiType,scoreValue,textureId,modelId,soundId
 * Lines with invalid data are skipped.
 */
public class EntityReader {

    private EntityReader() {
    }

    public static List<Enemy> readEnemies(Path filePath) throws IOException {
        List<Enemy> enemies = new ArrayList<>();

        try (Stream<String> lines = Files.lines(filePath)) {
            lines.filter(line -> line != null && !line.isBlank() && !line.startsWith("#"))
                    .forEach(line -> {
                        try {
                            Enemy enemy = parseLine(line);
                            enemies.add(enemy);
                        } catch (EntityParseException e) {
                            System.err.println("[EntityReader] Skipping invalid line: " + e.getMessage());
                        }
                    });
        }

        return enemies;
    }

    private static Enemy parseLine(String line) throws EntityParseException {
        String[] parts = line.split(",");
        if (parts.length != 9) {
            throw new EntityParseException("Expected 9 fields, got " + parts.length + " in: " + line);
        }

        String id = parts[0].trim();
        if (!EntityValidator.isValidId(id)) {
            throw new EntityParseException("Invalid id: '" + id + "'");
        }

        double x;
        double y;
        try {
            x = Double.parseDouble(parts[1].trim());
            y = Double.parseDouble(parts[2].trim());
        } catch (NumberFormatException e) {
            throw new EntityParseException("Invalid coordinates in: " + line);
        }
        if (!EntityValidator.isValidCoordinate(x) || !EntityValidator.isValidCoordinate(y)) {
            throw new EntityParseException("Coordinates out of range in: " + line);
        }

        int health;
        try {
            health = Integer.parseInt(parts[3].trim());
        } catch (NumberFormatException e) {
            throw new EntityParseException("Invalid health in: " + line);
        }
        if (!EntityValidator.isValidHealth(health)) {
            throw new EntityParseException("Health out of range [1..10000] in: " + line);
        }

        AiStrategy ai = parseAi(parts[4].trim(), line);

        int scoreValue;
        try {
            scoreValue = Integer.parseInt(parts[5].trim());
        } catch (NumberFormatException e) {
            throw new EntityParseException("Invalid scoreValue in: " + line);
        }
        if (!EntityValidator.isValidScoreValue(scoreValue)) {
            throw new EntityParseException("scoreValue must be >= 0 in: " + line);
        }

        String textureId = parts[6].trim();
        String modelId = parts[7].trim();
        String soundId = parts[8].trim();
        if (textureId.isBlank() || modelId.isBlank() || soundId.isBlank()) {
            throw new EntityParseException("Resource ids must not be blank in: " + line);
        }

        VisualResource resource = VisualResourceFactory.getResource(textureId, modelId, soundId);
        return new Enemy(id, x, y, health, resource, ai, scoreValue);
    }

    private static AiStrategy parseAi(String aiType, String line) throws EntityParseException {
        return switch (aiType.toUpperCase()) {
            case "AGGRESSIVE" -> new AggressiveAi();
            case "DEFENSIVE" -> new DefensiveAi();
            case "PATROL" -> new PatrolAi();
            default -> throw new EntityParseException("Unknown AI type: '" + aiType + "' in: " + line);
        };
    }
}
