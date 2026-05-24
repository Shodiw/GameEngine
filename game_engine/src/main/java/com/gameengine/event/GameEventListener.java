package com.gameengine.event;

public interface GameEventListener {

    void onEvent(GameEvent event, Object data);
}
