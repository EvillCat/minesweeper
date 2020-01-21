package ru.evillcat.event.minesweeper.type;

import ru.evillcat.common.mode.GameMode;
import ru.evillcat.event.bus.Event;

public class RedrawFieldEvent extends Event {

    private final GameMode gameMode;

    public RedrawFieldEvent(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameMode getGameMode() {
        return gameMode;
    }
}
