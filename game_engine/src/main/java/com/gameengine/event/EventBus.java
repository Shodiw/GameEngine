package com.gameengine.event;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class EventBus {

    private final Map<GameEvent, List<GameEventListener>> listeners = new EnumMap<>(GameEvent.class);

    public void subscribe(GameEvent event, GameEventListener listener) {
        listeners.computeIfAbsent(event, k -> new ArrayList<>()).add(listener);
    }

    public void unsubscribe(GameEvent event, GameEventListener listener) {
        List<GameEventListener> list = listeners.get(event);
        if (list != null) {
            list.remove(listener);
        }
    }

    public void publish(GameEvent event, Object data) {
        List<GameEventListener> list = listeners.get(event);
        if (list != null) {
            for (GameEventListener listener : list) {
                listener.onEvent(event, data);
            }
        }
    }
}
