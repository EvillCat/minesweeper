package ru.evillcat.model.field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.evillcat.common.cell.Cell;
import ru.evillcat.common.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmptyCellsCreator {

    private static final Logger logger = LoggerFactory.getLogger(EmptyCellsCreator.class);

    private final int height;
    private final int width;

    private final List<Cell> cellsToCheck;
    private final Set<Cell> checked;
    private final Set<Cell> markedCells;

    public EmptyCellsCreator(int fieldHeight, int fieldWidth, Cell firstEmptyCell, Field field) {
        this.height = fieldHeight - 1;
        this.width = fieldWidth - 1;
        cellsToCheck = new ArrayList<>();
        checked = new HashSet<>();
        markedCells = new HashSet<>();
        cellsToCheck.add(firstEmptyCell);
        openCellUntilUncheckedCellsExist(field);
    }

    private void openCellUntilUncheckedCellsExist(Field field) {
        int i = 0;
        do {
            openGroupOfCells(cellsToCheck.get(i++), field);
        } while (checked.size() != cellsToCheck.size());
    }

    private void openGroupOfCells(Cell firstEmptyCell, Field field) {
        logger.info("Cell: " + firstEmptyCell.getCoordinates().toString());

        int beginHeight = firstEmptyCell.getCoordinates().getHeight() - 1;
        int beginWidth = firstEmptyCell.getCoordinates().getWidth() - 1;
        int endHeight = firstEmptyCell.getCoordinates().getHeight() + 1;
        int endWidth = firstEmptyCell.getCoordinates().getWidth() + 1;

        if (beginHeight < 0) {
            beginHeight = firstEmptyCell.getCoordinates().getHeight();
        }
        if (beginWidth < 0) {
            beginWidth = firstEmptyCell.getCoordinates().getWidth();
        }
        if (endHeight > height) {
            endHeight = firstEmptyCell.getCoordinates().getHeight();
        }
        if (endWidth > width) {
            endWidth = firstEmptyCell.getCoordinates().getWidth();
        }
        getCellsAround(beginHeight, beginWidth, endHeight, endWidth, field);
        checked.add(firstEmptyCell);
    }

    private void getCellsAround(int beginHeight, int beginWidth, int endHeight, int endWidth, Field field) {
        for (int h = beginHeight; h <= endHeight; h++) {
            for (int w = beginWidth; w <= endWidth; w++) {
                Cell cell = field.getCell(new Coordinate(h, w));
                if (cell.getMinesNearCount() == 0 && !cellsToCheck.contains(cell)) {
                    cellsToCheck.add(cell);
                } else if (cell.getMinesNearCount() > 0) {
                    markedCells.add(cell);
                }
            }
        }
    }

    public List<Cell> createCellListOnOpen() {
        List<Cell> result = new ArrayList<>();
        Set<Cell> checkedCellsSet = new HashSet<>(checked);
        Set<Cell> markedCellsSet = new HashSet<>(markedCells);
        result.addAll(checkedCellsSet);
        result.addAll(markedCellsSet);
        return result;
    }
}
