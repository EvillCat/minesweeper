package ru.evillcat.event.minesweeper.type;

import ru.evillcat.common.mode.GameMode;
import ru.evillcat.event.bus.Event;

public class FieldCreateEvent extends Event {

    private final GameMode gameMode;

    public FieldCreateEvent(int height, int width, int minesCount) {
        gameMode = new GameMode(height, width, minesCount);
    }

    public GameMode getCreatedGameMode() {
        return gameMode;
    }
}
