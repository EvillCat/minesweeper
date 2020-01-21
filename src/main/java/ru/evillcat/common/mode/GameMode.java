package ru.evillcat.common.mode;

import java.util.Objects;

public class GameMode {

    private static final String SPACE = " ";

    private final int fieldHeight;
    private final int fieldWidth;
    private final int minesCount;

    public GameMode(int fieldHeight, int fieldWidth, int minesCount) {
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
        this.minesCount = minesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameMode gameMode = (GameMode) o;
        return fieldHeight == gameMode.fieldHeight &&
                fieldWidth == gameMode.fieldWidth &&
                minesCount == gameMode.minesCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldHeight, fieldWidth, minesCount);
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getDataCount() {
        return minesCount;
    }

    public String modeStringView() {
        return "h: " + fieldHeight + SPACE +
                "w: " + fieldWidth + SPACE +
                "mines: " + minesCount;
    }
}
