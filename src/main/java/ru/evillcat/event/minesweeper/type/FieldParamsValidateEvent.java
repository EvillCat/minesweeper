package ru.evillcat.event.minesweeper.type;

import ru.evillcat.common.mode.GameMode;
import ru.evillcat.event.bus.Event;

public class FieldParamsValidateEvent extends Event {

    private final GameMode mode;

    public FieldParamsValidateEvent(GameMode mode) {
        this.mode = mode;
    }

    public GameMode getMode() {
        return mode;
    }
}
