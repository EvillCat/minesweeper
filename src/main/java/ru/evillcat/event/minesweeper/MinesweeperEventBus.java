package ru.evillcat.event.minesweeper;

//import org.reflections.Reflections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.evillcat.event.bus.Event;
import ru.evillcat.event.bus.EventBus;
import ru.evillcat.event.bus.EventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinesweeperEventBus implements EventBus {

    private static final Logger logger = LoggerFactory.getLogger(MinesweeperEventBus.class);
    private static final String EVENT_TYPES_PACKAGE = ".type";

    private final Map<Class<? extends Event>, List<EventListener>> gameListeners;

    public MinesweeperEventBus() {
        gameListeners = new HashMap<>();
    }

    @Override
    public <T extends Event> void subscribe(Class<T> eventType, EventListener<T> listener) {
        gameListeners.computeIfAbsent(eventType, aClass -> {
            List<EventListener> newListenersList = new ArrayList<>();
            newListenersList.add(listener);
            return newListenersList;
        });
    }

    @Override
    public void publish(Event event) {
        List<EventListener> listeners = gameListeners.get(event.getClass());
        for (EventListener listener : listeners) {
            listener.update(event);
        }
    }
}
