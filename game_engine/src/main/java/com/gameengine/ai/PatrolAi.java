package com.gameengine.ai;

public class PatrolAi implements AiStrategy {

    private static final double PATROL_RANGE = 5.0;

    @Override
    public void execute(String entityId, double x, double y) {
        double patrolX = x + PATROL_RANGE;
        System.out.printf("[PatrolAI] Entity %s patrols between (%.1f, %.1f) and (%.1f, %.1f)%n",
                entityId, x, y, patrolX, y);
    }

    @Override
    public String getName() {
        return "PATROL";
    }
}
