package ru.evillcat.event.minesweeper.type;

import ru.evillcat.event.bus.Event;

public class ViewFlagUpdateEvent extends Event {

    private final int flagCount;

    public ViewFlagUpdateEvent(int flagCount) {
        this.flagCount = flagCount;
    }

    public int getFlagCount() {
        return flagCount;
    }
}
