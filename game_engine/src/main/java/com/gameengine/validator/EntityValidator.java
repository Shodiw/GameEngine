package com.gameengine.validator;

public class EntityValidator {

    private static final int MIN_HEALTH = 1;
    private static final int MAX_HEALTH = 10000;
    private static final double MIN_COORD = -10000.0;
    private static final double MAX_COORD = 10000.0;

    private EntityValidator() {
    }

    public static boolean isValidId(String id) {
        return id != null && !id.isBlank() && id.matches("[A-Za-z0-9_-]+");
    }

    public static boolean isValidHealth(int health) {
        return health >= MIN_HEALTH && health <= MAX_HEALTH;
    }

    public static boolean isValidCoordinate(double coord) {
        return coord >= MIN_COORD && coord <= MAX_COORD && Double.isFinite(coord);
    }

    public static boolean isValidScoreValue(int scoreValue) {
        return scoreValue >= 0;
    }
}
