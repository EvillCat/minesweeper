package ru.evillcat.model.field;

import ru.evillcat.common.cell.Cell;
import ru.evillcat.common.coordinate.Coordinate;

import java.util.*;

public class CellMarker {

    private final List<Coordinate> minesCoordinates;
    private final int fieldHeight;
    private final int fieldWidth;

    private final List<Cell> markedCells;

    public CellMarker(List<Coordinate> minesCoordinates, int fieldHeight, int fieldWidth) {
        this.minesCoordinates = minesCoordinates;
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
        markedCells = new ArrayList<>();
    }

    public List<Cell> markCellsNearMines() {
        int height = fieldHeight - 1;
        int width = fieldWidth - 1;

        for (Coordinate mineCoordinate : minesCoordinates) {
            int beginHeight = mineCoordinate.getHeight() - 1;
            int beginWidth = mineCoordinate.getWidth() - 1;
            int endHeight = mineCoordinate.getHeight() + 1;
            int endWidth = mineCoordinate.getWidth() + 1;
            if (beginHeight < 0) {
                beginHeight = mineCoordinate.getHeight();
            }
            if (beginWidth < 0) {
                beginWidth = mineCoordinate.getWidth();
            }
            if (endHeight > height) {
                endHeight = mineCoordinate.getHeight();
            }
            if (endWidth > width) {
                endWidth = mineCoordinate.getWidth();
            }
            markCells(beginHeight, beginWidth, endHeight, endWidth);
        }
        return markedCells;
    }

    private void markCells(int beginHeight, int beginWidth, int endHeight, int endWidth) {
        for (int h = beginHeight; h <= endHeight; h++) {
            for (int w = beginWidth; w <= endWidth; w++) {
                if (!isMine(h, w)) {
                    markCell(h, w);
                }
            }
        }
    }

    private void markCell(int height, int width) {
        Cell cell = new Cell(new Coordinate(height, width), false);
        if (!markedCells.contains(cell)) {
            cell.setMinesNearCount(cell.getMinesNearCount() + 1);
            markedCells.add(cell);
        } else {
            cell = markedCells.get(markedCells.indexOf(cell));
            cell.setMinesNearCount(cell.getMinesNearCount() + 1);
        }
    }

    private boolean isMine(int height, int width) {
        Coordinate coordinate = new Coordinate(height, width);
        return minesCoordinates.contains(coordinate);
    }
}
