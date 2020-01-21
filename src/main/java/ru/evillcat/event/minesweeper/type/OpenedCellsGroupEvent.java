package ru.evillcat.event.minesweeper.type;

import ru.evillcat.common.cell.Cell;
import ru.evillcat.event.bus.Event;

import java.util.List;

public class OpenedCellsGroupEvent extends Event {

    private final List<Cell> cells;

    public OpenedCellsGroupEvent(List<Cell> cells) {
        this.cells = cells;
    }

    public List<Cell> getCells() {
        return cells;
    }
}
