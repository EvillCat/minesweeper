package ru.evillcat.model.field;

import ru.evillcat.common.mode.GameMode;
import ru.evillcat.event.bus.EventBus;
import ru.evillcat.event.minesweeper.type.FieldParamsValidateEvent;

public class FieldValidator {

    private static final int HEIGHT_MIN_LENGTH = 1;
    private static final int WIDTH_MIN_LENGTH = 8;
    private static final int MINES_MIN_COUNT = 1;

    private static final int HEIGHT_MAX_LENGTH = 99;
    private static final int WIDTH_MAX_LENGTH = 99;

    private final EventBus eventBus;

    public FieldValidator(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public GameMode validateField(int h, int w, int minesCount) {
        int minesMaxCount = h * w - 1;
        if (h < HEIGHT_MIN_LENGTH) {
            h = HEIGHT_MIN_LENGTH;
        }
        if (w < WIDTH_MIN_LENGTH) {
            w = WIDTH_MIN_LENGTH;
        }
        if (h > HEIGHT_MAX_LENGTH) {
            h = HEIGHT_MAX_LENGTH;
        }
        if (w > WIDTH_MAX_LENGTH) {
            w = WIDTH_MAX_LENGTH;
        }
        if (minesCount < MINES_MIN_COUNT) {
            minesCount = MINES_MIN_COUNT;
        }
        if (minesCount > minesMaxCount) {
            minesCount = minesMaxCount;
        }
        GameMode mode = new GameMode(h, w, minesCount);
        eventBus.publish(new FieldParamsValidateEvent(mode));
        return mode;
    }
}
