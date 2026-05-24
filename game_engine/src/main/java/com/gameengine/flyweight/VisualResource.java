package com.gameengine.flyweight;

import java.util.Objects;

public class VisualResource {

    private final String textureId;
    private final String modelId;
    private final String soundId;

    public VisualResource(String textureId, String modelId, String soundId) {
        this.textureId = textureId;
        this.modelId = modelId;
        this.soundId = soundId;
    }

    public void render(double x, double y) {
        System.out.printf("[Render] texture=%s model=%s sound=%s at (%.1f, %.1f)%n",
                textureId, modelId, soundId, x, y);
    }

    public String getTextureId() {
        return textureId;
    }

    public String getModelId() {
        return modelId;
    }

    public String getSoundId() {
        return soundId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VisualResource that)) {
            return false;
        }
        return textureId.equals(that.textureId)
                && modelId.equals(that.modelId)
                && soundId.equals(that.soundId);
    }

    @Override
    public int hashCode() {
        int result = textureId.hashCode();
        result = 31 * result + modelId.hashCode();
        result = 31 * result + soundId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "VisualResource{"
                + "textureId='" + textureId + '\''
                + ", modelId='" + modelId + '\''
                + ", soundId='" + soundId + '\''
                + '}';
    }
}
