package ru.evillcat.model.field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.evillcat.common.cell.Cell;
import ru.evillcat.common.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private static final Logger logger = LoggerFactory.getLogger(Field.class);

    private final List<Cell> minesCells;
    private final List<Cell> markedCells;

    public Field(List<Coordinate> minesCellsCoordinates, List<Cell> markedCells) {
        logger.info("Mines on field: " + minesCellsCoordinates.size() + " "
                + "Marked cells on field: " + markedCells.size());
        minesCells = new ArrayList<>();
        for (Coordinate coordinate : minesCellsCoordinates) {
            minesCells.add(new Cell(coordinate, true));
        }
        this.markedCells = markedCells;
    }

    public Cell getCell(Coordinate coordinate) {
        for (Cell mineCell : minesCells) {
            if (mineCell.getCoordinates().equals(coordinate)) {
                return new Cell(coordinate, true);
            }
        }
        for (Cell labeledCell : markedCells) {
            if (labeledCell.getCoordinates().equals(coordinate)) {
                return labeledCell;
            }
        }
        return new Cell(coordinate, false);
    }

    public List<Cell> getMinesCells() {
        return minesCells;
    }
}
