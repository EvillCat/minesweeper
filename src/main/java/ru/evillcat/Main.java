package ru.evillcat;

import ru.evillcat.context.view.DefaultViewImagesSetter;
import ru.evillcat.context.view.listener.TimerListener;
import ru.evillcat.context.view.listener.ViewEventsListener;
import ru.evillcat.controller.Controller;
import ru.evillcat.event.bus.EventBus;
import ru.evillcat.event.minesweeper.MinesweeperEventBus;
import ru.evillcat.model.field.FieldGenerator;
import ru.evillcat.model.field.FieldValidator;
import ru.evillcat.score.FileSystemScoreSaver;
import ru.evillcat.score.ScoreSaver;
import ru.evillcat.score.reader.ScoreFileReader;
import ru.evillcat.score.reader.ScoreReader;
import ru.evillcat.score.writer.ScoreFileWriter;
import ru.evillcat.timer.GameTimer;
import ru.evillcat.view.GameView;
import ru.evillcat.view.MainView;
import ru.evillcat.view.element.face.ButtonWrapper;
import ru.evillcat.view.element.face.FaceButtonWrapper;
import ru.evillcat.view.element.flag.FlagCounter;
import ru.evillcat.view.element.flag.OldStyleFlagCounter;
import ru.evillcat.view.element.panel.GameInfoPanel;
import ru.evillcat.view.element.timer.OldStyleTimer;
import ru.evillcat.view.element.timer.ViewTimer;

public class Main {

    private static final String SCORES_FILE_PATH = "scores.txt";

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");

        EventBus eventBus = new MinesweeperEventBus();
        ViewTimer viewTimer = new OldStyleTimer();
        FlagCounter flagCounter = new OldStyleFlagCounter(eventBus);
        ButtonWrapper buttonWrapper = new FaceButtonWrapper(eventBus);
        GameInfoPanel infoPanel = new GameInfoPanel(viewTimer, buttonWrapper, flagCounter);

        TimerListener timerListener = new TimerListener(viewTimer);
        timerListener.subscribeListeners(eventBus);
        GameTimer timer = new GameTimer(eventBus);
        ScoreReader<String> scoreReader = new ScoreFileReader(SCORES_FILE_PATH);
        ScoreSaver<String> scoreSaver = new FileSystemScoreSaver(
                new ScoreFileWriter(SCORES_FILE_PATH, scoreReader), scoreReader);
        FieldValidator fieldValidator = new FieldValidator(eventBus);
        FieldGenerator fieldGenerator = new FieldGenerator(eventBus);
        GameView view = new MainView(new Controller(fieldGenerator, fieldValidator, scoreSaver, timer, eventBus),
                infoPanel.getPanel(), eventBus);
        ViewEventsListener viewEventsListener = new ViewEventsListener(view);
        viewEventsListener.subscribeListeners(eventBus);
        DefaultViewImagesSetter imagesSetter = new DefaultViewImagesSetter(view);
        imagesSetter.setDefaultViewImages();
        view.onViewStart();
    }
}
