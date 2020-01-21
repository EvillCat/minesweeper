package ru.evillcat.event.minesweeper.type;

import ru.evillcat.event.bus.Event;

public class FlagCountReinitializedEvent extends Event {

    private final int totalFlagsCount;

    public FlagCountReinitializedEvent(int totalFlagsCount) {
        this.totalFlagsCount = totalFlagsCount;
    }

    public int getTotalFlagsCount() {
        return totalFlagsCount;
    }
}
