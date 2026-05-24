package com.gameengine.exception;

public class GameEngineException extends Exception {

    public GameEngineException(String message) {
        super(message);
    }

    public GameEngineException(String message, Throwable cause) {
        super(message, cause);
    }
}
