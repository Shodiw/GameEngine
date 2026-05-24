package com.gameengine.flyweight;

import java.util.HashMap;
import java.util.Map;

public class VisualResourceFactory {

    private static final Map<String, VisualResource> cache = new HashMap<>();

    private VisualResourceFactory() {
    }

    public static VisualResource getResource(String textureId, String modelId, String soundId) {
        String key = textureId + "|" + modelId + "|" + soundId;
        return cache.computeIfAbsent(key, k -> {
            System.out.println("[Flyweight] Creating new VisualResource: " + key);
            return new VisualResource(textureId, modelId, soundId);
        });
    }

    public static int getCacheSize() {
        return cache.size();
    }

    public static void clearCache() {
        cache.clear();
    }
}
