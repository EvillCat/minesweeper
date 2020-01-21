package ru.evillcat.event.minesweeper.type;

import ru.evillcat.common.mode.GameMode;
import ru.evillcat.event.bus.Event;

import java.util.List;

public class DefaultModesInformationNeedEvent extends Event {

    private final List<GameMode> defaultGameModes;

    public DefaultModesInformationNeedEvent(List<GameMode> defaultGameModes) {
        this.defaultGameModes = defaultGameModes;
    }

    public List<GameMode> getDefaultGameModes() {
        return defaultGameModes;
    }
}
