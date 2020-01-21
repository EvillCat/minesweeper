package ru.evillcat.view.element.menu;

import ru.evillcat.common.mode.GameMode;
import ru.evillcat.event.bus.EventBus;
import ru.evillcat.event.bus.EventListener;
import ru.evillcat.event.minesweeper.type.DefaultModesInformationNeedEvent;
import ru.evillcat.event.minesweeper.type.FieldParamsValidateEvent;
import ru.evillcat.event.minesweeper.type.ScoresRequestEvent;
import ru.evillcat.event.minesweeper.type.TimerStopEvent;
import ru.evillcat.view.GameView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MinesweeperMenuBar extends JMenuBar {

    private static final String EXIT = "Exit";
    private static final String ABOUT = "About";
    private static final String NEW_GAME = "New game";
    private static final String HIGH_SCORES = "High scores";
    private static final String RULES = "MINESWEEPER DESCRIPTION";
    private final GameView view;
    private final EventBus eventBus;
    private List<String> scoresList;
    private GameMode chosenGameMode;
    private List<GameMode> modes;
    private final List<JTextField> fields = new ArrayList<>();


    public MinesweeperMenuBar(GameView view, EventBus eventBus) {
        this.view = view;
        this.eventBus = eventBus;
        eventBus.subscribe(ScoresRequestEvent.class, new ScoresListener());
        eventBus.subscribe(FieldParamsValidateEvent.class, new CustomFieldParamsListener());
        eventBus.subscribe(DefaultModesInformationNeedEvent.class, new GameModesInfoListener());
        JTextField heightField = new JTextField("h", 5);
        JTextField widthField = new JTextField("w", 5);
        JTextField minesField = new JTextField("mines", 5);

        heightField.setEnabled(false);
        widthField.setEnabled(false);
        minesField.setEnabled(false);

        fields.add(heightField);
        fields.add(widthField);
        fields.add(minesField);

        view.needDefaultGameModes();
        createMenu();
    }

    private void createMenu() {
        JMenu menu = new JMenu("Menu");
        JMenuItem newGame = new JMenuItem(NEW_GAME);
        newGame.addActionListener(actionEvent -> {
            view.needDefaultGameModes();
            JDialog newGameDialog = new JDialog(view.getFrame(), "New Game", true);
            newGameDialog.setLayout(new FlowLayout());
            newGameDialog.setPreferredSize(new Dimension(500, 300));
            newGameDialog.setMinimumSize(new Dimension(500, 300));
            newGameDialog.setLocationRelativeTo(view.getFrame());
            JPanel buttonsPanel = new JPanel(new GridLayout((modes.size() + 3), 0));
            ButtonGroup buttonGroup = new ButtonGroup();

            for (GameMode gameMode : modes) {
                JRadioButton radioButton = new JRadioButton(gameMode.modeStringView());
                radioButton.addActionListener(actionEvent12 -> {
                    chosenGameMode = gameMode;
                    disableCustomFieldSelectionComponents(fields);
                });
                buttonsPanel.add(radioButton);
                buttonGroup.add(radioButton);
            }

            JRadioButton customRadioButton = new JRadioButton("Custom");
            customRadioButton.addActionListener(actionEvent1 -> enableCustomFieldSelectionComponents(fields));
            buttonsPanel.add(customRadioButton);
            buttonGroup.add(customRadioButton);


            JPanel customModeParamsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            for (JTextField component : fields) {
                customModeParamsPanel.add(component);
            }
            buttonsPanel.add(customModeParamsPanel);

            JButton startButton = new JButton("Start");
            startButton.addActionListener(actionEvent1 -> {
                if (fields.get(0).isEnabled()) {
                    int height = Integer.parseInt(fields.get(0).getText());
                    int width = Integer.parseInt(fields.get(1).getText());
                    int count = Integer.parseInt(fields.get(2).getText());
                    chosenGameMode = new GameMode(height, width, count);
                }
                eventBus.publish(new TimerStopEvent());

                view.changeChosenGameMode(chosenGameMode);
            });
            buttonsPanel.add(startButton);
            newGameDialog.add(buttonsPanel);
            newGameDialog.setResizable(false);
            newGameDialog.setVisible(true);
        });

        JMenuItem scores = new JMenuItem(HIGH_SCORES);

        scores.addActionListener(actionEvent -> {
            view.onScoresRequest();
            JDialog scoresDialog = new JDialog(view.getFrame(), "Scores", true);
            scoresDialog.setLayout(new FlowLayout(FlowLayout.LEADING, 30, 10));
            scoresDialog.setPreferredSize(new Dimension(500, 300));
            scoresDialog.setMinimumSize(new Dimension(500, 300));
            scoresDialog.setLocationRelativeTo(view.getFrame());
            for (String score : scoresList) {
                scoresDialog.add(new JLabel(score));
            }
            scoresDialog.setResizable(false);
            scoresDialog.setVisible(true);

        });

        JMenuItem about = new JMenuItem(ABOUT);
        about.addActionListener(actionEvent -> {
            JDialog aboutDialog = new JDialog(view.getFrame(), "About", true);
            aboutDialog.setLayout(new BorderLayout());
            aboutDialog.setPreferredSize(new Dimension(200, 300));
            aboutDialog.setMinimumSize(new Dimension(200, 300));
            aboutDialog.add(new JLabel(RULES), BorderLayout.NORTH);
            aboutDialog.setLocationRelativeTo(view.getFrame());
            aboutDialog.setResizable(false);
            aboutDialog.setVisible(true);
        });

        JMenuItem exit = new JMenuItem(EXIT);
        exit.addActionListener(actionEvent -> System.exit(0));

        menu.add(newGame);
        menu.add(scores);
        menu.addSeparator();
        menu.add(about);
        menu.addSeparator();
        menu.add(exit);
        this.add(menu);
    }

    private void enableCustomFieldSelectionComponents(List<JTextField> components) {
        for (JTextField component : components) {
            component.setEnabled(true);
        }
    }

    private void disableCustomFieldSelectionComponents(List<JTextField> components) {
        for (JTextField component : components) {
            component.setEnabled(false);
        }
    }

    private class ScoresListener implements EventListener<ScoresRequestEvent> {

        @Override
        public void update(ScoresRequestEvent event) {
            scoresList = event.getScores();
        }
    }

    private class CustomFieldParamsListener implements EventListener<FieldParamsValidateEvent> {

        @Override
        public void update(FieldParamsValidateEvent event) {
            GameMode currentMode = event.getMode();
            fields.get(0).setText(String.valueOf(currentMode.getFieldHeight()));
            fields.get(1).setText(String.valueOf(currentMode.getFieldWidth()));
            fields.get(2).setText(String.valueOf(currentMode.getDataCount()));
        }
    }

    private class GameModesInfoListener implements EventListener<DefaultModesInformationNeedEvent> {

        @Override
        public void update(DefaultModesInformationNeedEvent event) {
            modes = event.getDefaultGameModes();
        }
    }
}
