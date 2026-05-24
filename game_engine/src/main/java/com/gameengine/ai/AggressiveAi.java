package com.gameengine.ai;

public class AggressiveAi implements AiStrategy {

    @Override
    public void execute(String entityId, double x, double y) {
        System.out.printf("[AggressiveAI] Entity %s charges toward player at (%.1f, %.1f)%n",
                entityId, x, y);
    }

    @Override
    public String getName() {
        return "AGGRESSIVE";
    }
}
