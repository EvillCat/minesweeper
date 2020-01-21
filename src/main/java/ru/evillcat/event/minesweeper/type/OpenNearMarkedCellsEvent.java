package ru.evillcat.event.minesweeper.type;

import ru.evillcat.common.coordinate.Coordinate;
import ru.evillcat.event.bus.Event;

public class OpenNearMarkedCellsEvent extends Event {

    private final Coordinate coordinate;

    public OpenNearMarkedCellsEvent(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
