package ru.evillcat.context.view.listener;

import ru.evillcat.common.cell.Cell;
import ru.evillcat.common.mode.GameMode;
import ru.evillcat.context.view.image.ButtonImage;
import ru.evillcat.event.bus.EventBus;
import ru.evillcat.event.bus.EventListener;
import ru.evillcat.event.minesweeper.type.*;
import ru.evillcat.view.GameView;

import java.awt.*;
import java.util.List;

public class ViewEventsListener {

    private final GameView view;

    public ViewEventsListener(GameView view) {
        this.view = view;
    }

    public void subscribeListeners(EventBus eventBus) {
        eventBus.subscribe(CellOpenedEvent.class, new CellStateListener());
        eventBus.subscribe(OpenedCellsGroupEvent.class, new GroupOfCellsStateListener());
        eventBus.subscribe(GameLostEvent.class, new GameLostListener());
        eventBus.subscribe(GameWonEvent.class, new GameWinListener());
        eventBus.subscribe(DefaultModeRequiredEvent.class, new DefaultModeListener());
        eventBus.subscribe(FieldCreateEvent.class, new FieldCreationListener());
        eventBus.subscribe(RedrawFieldEvent.class, (event) -> view.reDrawField(event.getGameMode()));
        eventBus.subscribe(FlagCountUpdateEvent.class, new FlagStateListener());
    }

    private Image determineImage(Cell cell) {
        return cell.getMinesNearCount() == 0 ?
                ButtonImage.getEmptyImage() : ButtonImage.getImageByNumber(cell.getMinesNearCount());
    }

    private String determineImageName(Cell cell) {
        return cell.getMinesNearCount() == 0 ?
                ButtonImage.EMPTY : ButtonImage.getImageName(cell.getMinesNearCount());
    }

    private class DefaultModeListener implements EventListener<DefaultModeRequiredEvent> {

        @Override
        public void update(DefaultModeRequiredEvent event) {
            GameMode gameMode = event.getDefaultGameMode();
            view.drawView(gameMode);
        }
    }

    private class FieldCreationListener implements EventListener<FieldCreateEvent> {

        @Override
        public void update(FieldCreateEvent event) {
            view.changeChosenGameMode(event.getCreatedGameMode());
        }

    }

    private class CellStateListener implements EventListener<CellOpenedEvent> {

        @Override
        public void update(CellOpenedEvent event) {
            Cell cell = event.getCell();
            Image image = determineImage(cell);
            String imageName = determineImageName(cell);
            view.setButtonImage(cell.getCoordinates(), image, imageName);
        }
    }

    private class GroupOfCellsStateListener implements EventListener<OpenedCellsGroupEvent> {

        @Override
        public void update(OpenedCellsGroupEvent event) {
            List<Cell> cells = event.getCells();
            Image image;
            String imageName;
            for (Cell cell : cells) {
                if (!cell.isMine()) {
                    image = determineImage(cell);
                    imageName = determineImageName(cell);
                } else {
                    image = ButtonImage.getMineImage();
                    imageName = ButtonImage.MINE;
                }
                view.setButtonImage(cell.getCoordinates(), image, imageName);
            }
        }
    }

    private class GameLostListener implements EventListener<GameLostEvent> {

        @Override
        public void update(GameLostEvent event) {
            List<Cell> mines = event.getCells();
            for (Cell cell : mines) {
                view.setButtonImage(cell.getCoordinates(), ButtonImage.getMineImage(), ButtonImage.MINE);
            }
            view.deactivateAllButtonsOnField();
            view.gameLost();
        }
    }

    private class GameWinListener implements EventListener<GameWonEvent> {

        @Override
        public void update(GameWonEvent event) {
            view.deactivateAllButtonsOnField();
            view.gameWon();
        }
    }

    private class FlagStateListener implements EventListener<FlagCountUpdateEvent> {

        @Override
        public void update(FlagCountUpdateEvent event) {
            view.updateFlagCount(event.getFlagsCount());
            view.setFlagOnSelectedButton();
        }
    }
}
