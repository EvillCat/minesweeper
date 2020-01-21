package ru.evillcat.event.minesweeper.type;

import ru.evillcat.common.mode.GameMode;
import ru.evillcat.event.bus.Event;

public class DefaultModeRequiredEvent extends Event {

    private final GameMode defaultGameMode;

    public DefaultModeRequiredEvent(GameMode defaultGameMode) {
        this.defaultGameMode = defaultGameMode;
    }

    public GameMode getDefaultGameMode() {
        return defaultGameMode;
    }
}
