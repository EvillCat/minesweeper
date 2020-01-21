package ru.evillcat.model.field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.evillcat.common.cell.Cell;
import ru.evillcat.common.coordinate.Coordinate;
import ru.evillcat.common.mode.GameMode;
import ru.evillcat.event.bus.EventBus;
import ru.evillcat.event.bus.EventListener;
import ru.evillcat.event.minesweeper.type.*;
import ru.evillcat.model.field.generator.RegionsMinesGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FieldService {

    private static final Logger logger = LoggerFactory.getLogger(FieldService.class);

    private final int height;
    private final int width;
    private final Field field;
    private final EventBus eventBus;

    private final int notMinesCellsCount;
    private final Set<Cell> openedCells;
    private final List<Cell> flaggedCells;
    private int flagCount;

    public FieldService(GameMode gameMode, RegionsMinesGenerator minesGenerator, EventBus eventBus) {
        this.eventBus = eventBus;
        this.height = gameMode.getFieldHeight();
        this.width = gameMode.getFieldWidth();
        eventBus.subscribe(FlagCountReinitializedEvent.class, new FlagCountValueListener());
        flaggedCells = new ArrayList<>();
        flagCount = gameMode.getDataCount();
        List<Coordinate> minesCoordinates = minesGenerator.createMinesCoordinates();
        CellMarker cellMarker = new CellMarker(minesCoordinates, height, width);
        List<Cell> markedCells = cellMarker.markCellsNearMines();
        field = new Field(minesCoordinates, markedCells);
        notMinesCellsCount = (height * width) - minesCoordinates.size();
        openedCells = new HashSet<>();
    }

    public void openCell(Coordinate coordinate) {
        Cell cell = field.getCell(coordinate);
        logger.info("Cell opened : " + coordinate.toString() + " is mine: " + cell.isMine()
                + " mines near: " + cell.getMinesNearCount());
        if (cell.isMine()) {
            eventBus.publish(new GameLostEvent(field.getMinesCells()));
        } else if (!openedCells.contains(cell)) {
            if (cell.getMinesNearCount() == 0) {
                EmptyCellsCreator emptyCellsCreator = new EmptyCellsCreator(height, width, cell, field);
                List<Cell> emptyCells = emptyCellsCreator.createCellListOnOpen();
                eventBus.publish(new OpenedCellsGroupEvent(emptyCells));
                openedCells.addAll(emptyCells);
                logger.info("Empty Cells size: " + emptyCells.size());
            } else {
                eventBus.publish(new CellOpenedEvent(cell));
                openedCells.add(cell);
            }
        }
        isGameWon();
    }

    private void isGameWon() {
        logger.info("Is won: " + (notMinesCellsCount == openedCells.size()) + " " + "total: " + notMinesCellsCount
                + " " + "opened: " + openedCells.size());
        if (notMinesCellsCount == openedCells.size()) {
            eventBus.publish(new GameWonEvent());
        }
    }

    public void changeCellFlagState(Coordinate coordinate) {
        if (flagCount != 0) {
            Cell cell = new Cell(coordinate, false);
            if (flaggedCells.contains(cell)) {
                flaggedCells.remove(new Cell(coordinate, false));
                flagCount++;
            } else {
                flaggedCells.add(new Cell(coordinate, false));
                flagCount--;
            }
        }
        eventBus.publish(new FlagCountUpdateEvent(flagCount));
    }


    public void openAllCellsAroundOpened(Coordinate coordinate) {
        Cell cell = field.getCell(coordinate);
        int minesNearCellCount = cell.getMinesNearCount();
        int flaggedAround = 0;
        List<Coordinate> cellsToOpenCoordinates = new ArrayList<>();

        int buttonHeight = coordinate.getHeight();
        int buttonWidth = coordinate.getWidth();
        int beginHeight = buttonHeight == 0 ? 0 : buttonHeight - 1;
        int endHeight = buttonHeight == height - 1 ? height - 1 : buttonHeight + 1;
        int beginWidth = buttonWidth == 0 ? 0 : buttonWidth - 1;
        int endWidth = buttonWidth == width - 1 ? width - 1 : buttonWidth + 1;

        Coordinate cacheCoordinate;
        for (int h = beginHeight; h <= endHeight; h++) {
            for (int w = beginWidth; w <= endWidth; w++) {
                cacheCoordinate = new Coordinate(h, w);
                if (flaggedCells.contains(new Cell(cacheCoordinate, false))) {
                    flaggedAround++;
                } else {
                    cellsToOpenCoordinates.add(cacheCoordinate);
                }
            }
        }

        if (flaggedAround == minesNearCellCount) {
            for (Coordinate coordinate1 : cellsToOpenCoordinates) {
                openCell(coordinate1);
            }
        }
    }

    private class FlagCountValueListener implements EventListener<FlagCountReinitializedEvent> {

        @Override
        public void update(FlagCountReinitializedEvent event) {
            flagCount = event.getTotalFlagsCount();
        }
    }
}
