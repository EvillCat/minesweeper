package ru.evillcat.event.minesweeper.type;

import ru.evillcat.event.bus.Event;

public class TimerTickEvent extends Event {

    private final int seconds;

    public TimerTickEvent(int seconds) {
        this.seconds = seconds;
    }

    public int getTime() {
        return seconds;
    }
}
