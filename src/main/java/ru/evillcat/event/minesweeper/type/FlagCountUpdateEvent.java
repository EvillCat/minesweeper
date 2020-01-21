package ru.evillcat.event.minesweeper.type;

import ru.evillcat.event.bus.Event;

public class FlagCountUpdateEvent extends Event {

    private final int flagsCount;

    public FlagCountUpdateEvent(int flagsCount) {
        this.flagsCount = flagsCount;
    }

    public int getFlagsCount() {
        return flagsCount;
    }
}
