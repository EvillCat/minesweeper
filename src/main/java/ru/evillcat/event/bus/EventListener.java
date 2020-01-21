package ru.evillcat.event.bus;

public interface EventListener<T extends Event> {

    void update(T event);
}
