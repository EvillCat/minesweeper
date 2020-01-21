package ru.evillcat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.evillcat.common.coordinate.Coordinate;
import ru.evillcat.common.mode.GameMode;
import ru.evillcat.event.bus.EventBus;
import ru.evillcat.event.bus.EventListener;
import ru.evillcat.event.minesweeper.type.*;
import ru.evillcat.model.field.FieldGenerator;
import ru.evillcat.model.field.FieldService;
import ru.evillcat.model.field.FieldValidator;
import ru.evillcat.score.ScoreSaver;
import ru.evillcat.timer.GameTimer;

public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private final FieldGenerator fieldGenerator;
    private final FieldValidator fieldValidator;
    private final ScoreSaver<String> scoreSaver;
    private final GameTimer timer;
    private final Thread timerThread;
    private final EventBus eventBus;
    private boolean isFirstTimeTimerStart = true;
    private FieldService fieldService;
    private int fieldHeight;
    private int fieldWidth;
    private int minesCount;

    public Controller(FieldGenerator fieldGenerator, FieldValidator fieldValidator,
                      ScoreSaver<String> scoreSaver, GameTimer timer, EventBus eventBus) {
        this.fieldGenerator = fieldGenerator;
        this.fieldValidator = fieldValidator;
        this.scoreSaver = scoreSaver;
        this.timer = timer;
        this.eventBus = eventBus;
        timerThread = new Thread(timer::run);
        eventBus.subscribe(RecreateFieldEvent.class, new FieldRecreateListener());
        eventBus.subscribe(TimerStopEvent.class, new TimerStopListener());
    }

    public void onLoadContext() {
        fieldGenerator.loadDefaultModes();
    }

    public void onChangeFieldParams(GameMode gameMode) {
        GameMode mode = fieldValidator.validateField(gameMode.getFieldHeight(), gameMode.getFieldWidth(), gameMode.getDataCount());
        this.fieldHeight = mode.getFieldHeight();
        this.fieldWidth = mode.getFieldWidth();
        this.minesCount = mode.getDataCount();
        eventBus.publish(new FlagCountUpdateEvent(minesCount));
    }

    private void onCreateField(Coordinate firstClickCoordinate) {

        eventBus.publish(new TimerStartEvent());
        if (isFirstTimeTimerStart) {
            timerThread.start();
            isFirstTimeTimerStart = false;
        } else {
            timer.resetTimer();
            timer.restart();
        }
        fieldService = fieldGenerator.generateField(fieldHeight, fieldWidth, minesCount, firstClickCoordinate);
    }

    public void cellClicked(Coordinate coordinate) {
        fieldService.openCell(coordinate);
    }

    public void firstCellClicked(Coordinate coordinate) {
        onCreateField(coordinate);
        fieldService.openCell(coordinate);
    }

    public void onGameOver(boolean state, int clickCounter) {

        if (state) {
            timer.stopTimer();
            logger.info("Score trying to generate for " + fieldHeight + "x" + fieldWidth + " " + minesCount + " mines "
                    + timer.getSeconds() + " seconds");
            scoreSaver.write(fieldHeight, fieldWidth, minesCount, timer.getSeconds());
        } else if (clickCounter != 0) {
            timer.stopTimer();
        }
    }

    public void onScoresRequest() {
        eventBus.publish(new ScoresRequestEvent(scoreSaver.getScores()));
    }

    public void onNeedDefaultGameModeInfo() {
        fieldGenerator.sendDefaultModeInformation();
    }

    public void rightMouseClicked(Coordinate coordinate) {
        fieldService.changeCellFlagState(coordinate);
    }

    public void openNearMarkedButton(Coordinate coordinate) {
        fieldService.openAllCellsAroundOpened(coordinate);
    }

    private class FieldRecreateListener implements EventListener<RecreateFieldEvent> {

        @Override
        public void update(RecreateFieldEvent event) {
            eventBus.publish(new RedrawFieldEvent(new GameMode(fieldHeight, fieldWidth, minesCount)));
        }
    }

    private class TimerStopListener implements EventListener<TimerStopEvent> {

        @Override
        public void update(TimerStopEvent event) {
            timer.stopTimer();
        }
    }
}
