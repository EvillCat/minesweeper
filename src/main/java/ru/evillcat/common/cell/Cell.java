package ru.evillcat.common.cell;

import ru.evillcat.common.coordinate.Coordinate;

import java.util.Objects;

public class Cell {

    private final Coordinate coordinates;
    private final boolean isMine;
    private int minesNearCount = 0;

    public Cell(Coordinate coordinates, boolean isMine) {
        this.coordinates = coordinates;
        this.isMine = isMine;
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public boolean isMine() {
        return isMine;
    }

    public int getMinesNearCount() {
        return minesNearCount;
    }

    public void setMinesNearCount(int minesNearCount) {
        this.minesNearCount = minesNearCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cell cell = (Cell) o;
        return coordinates.equals(cell.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }
}
