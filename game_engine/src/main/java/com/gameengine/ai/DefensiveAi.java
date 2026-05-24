package com.gameengine.ai;

public class DefensiveAi implements AiStrategy {

    @Override
    public void execute(String entityId, double x, double y) {
        System.out.printf("[DefensiveAI] Entity %s retreats from player, current pos (%.1f, %.1f)%n",
                entityId, x, y);
    }

    @Override
    public String getName() {
        return "DEFENSIVE";
    }
}
