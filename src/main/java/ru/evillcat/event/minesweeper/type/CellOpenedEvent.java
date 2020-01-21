package ru.evillcat.event.minesweeper.type;

import ru.evillcat.common.cell.Cell;
import ru.evillcat.event.bus.Event;

public class CellOpenedEvent extends Event {

    private final Cell cell;

    public CellOpenedEvent(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }
}
