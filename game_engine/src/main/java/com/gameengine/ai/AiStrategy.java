package com.gameengine.ai;

public interface AiStrategy {

    void execute(String entityId, double x, double y);

    String getName();
}
