package com.gameengine.exception;

public class EntityParseException extends GameEngineException {

    public EntityParseException(String message) {
        super(message);
    }

    public EntityParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
