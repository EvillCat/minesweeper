package ru.evillcat.view;

import ru.evillcat.common.coordinate.Coordinate;
import ru.evillcat.common.mode.GameMode;
import ru.evillcat.controller.Controller;
import ru.evillcat.event.bus.EventBus;
import ru.evillcat.event.minesweeper.type.*;
import ru.evillcat.view.custom.button.SpriteButton;
import ru.evillcat.view.element.GameField;
import ru.evillcat.view.element.menu.MinesweeperMenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainView extends JFrame implements GameView {

    private final Controller controller;
    private final ActionListener actionListener = createActionListener();
    private final MouseAdapter mouseAdapter = createMouseAdapter();
    private final EventBus eventBus;

    private JScrollPane gameFieldJPanel;
    private Image flagImage;
    private Image blockImage;
    private String flagImageName;
    private String blockImageName;
    private final JPanel infoPanel;
    private GameField gameField;
    private SpriteButton flaggedButton;
    private int clickCounter = 0;

    public MainView(Controller controller, JPanel infoPanel,
                    EventBus eventBus) {
        super("Mine Sweeper");
        this.eventBus = eventBus;
        this.controller = controller;
        this.infoPanel = infoPanel;
        eventBus.subscribe(OpenNearMarkedCellsEvent.class,
                (event -> controller.openNearMarkedButton(event.getCoordinate())));
        eventBus.subscribe(FieldParamsValidateEvent.class, (event) -> {
        });
    }

    @Override
    public void setDefaultFlagImage(Image image) {
        flagImage = image;
    }

    @Override
    public void setDefaultBlockImage(Image image) {
        blockImage = image;
    }

    @Override
    public void setDefaultFlagImageName(String imageName) {
        flagImageName = imageName;
    }

    @Override
    public void setDefaultBlockImageName(String imageName) {
        blockImageName = imageName;
    }

    @Override
    public void onViewStart() {
        controller.onLoadContext();
    }

    @Override
    public void drawView(GameMode defaultGameMode) {
        setMaximumSize(new Dimension(800, 600));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JMenuBar menuBar = new MinesweeperMenuBar(this, eventBus);
        add(menuBar, BorderLayout.NORTH);

        gameField = new GameField(defaultGameMode.getFieldHeight(), defaultGameMode.getFieldWidth(),
                actionListener, mouseAdapter, eventBus, blockImage);
        controller.onChangeFieldParams(defaultGameMode);
        gameFieldJPanel = gameField.getJPanel();
        add(gameFieldJPanel, BorderLayout.SOUTH);
        add(infoPanel, BorderLayout.CENTER);

        pack();
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void changeChosenGameMode(GameMode gameMode) {
        reDrawField(gameMode);
    }

    @Override
    public void reDrawField(GameMode gameMode) {
        controller.onChangeFieldParams(gameMode);
        clickCounter = 0;
        controller.onGameOver(false, clickCounter);

        remove(gameFieldJPanel);
        gameField = new GameField(gameMode.getFieldHeight(), gameMode.getFieldWidth(),
                actionListener, mouseAdapter, eventBus, blockImage);
        gameFieldJPanel = gameField.getJPanel();
        add(gameFieldJPanel, BorderLayout.SOUTH);
        validate();
        repaint();
        pack();
    }

    @Override
    public void updateFlagCount(int flagCount) {
        eventBus.publish(new ViewFlagUpdateEvent(flagCount));
    }

    @Override
    public void setButtonImage(Coordinate coordinate, Image image, String imageName) {
        gameField.setButtonImage(coordinate, image, imageName);
    }

    @Override
    public void deactivateAllButtonsOnField() {
        gameField.deactivateAllButtons();
    }

    @Override
    public void gameLost() {
        controller.onGameOver(false, clickCounter);
    }

    @Override
    public void gameWon() {
        controller.onGameOver(true, clickCounter);
    }

    @Override
    public void onScoresRequest() {
        controller.onScoresRequest();
    }

    @Override
    public JFrame getFrame() {
        return this;
    }

    @Override
    public void needDefaultGameModes() {
        controller.onNeedDefaultGameModeInfo();
    }

    private ActionListener createActionListener() {
        return actionEvent -> {
            SpriteButton button = (SpriteButton) actionEvent.getSource();
            cellClicked(button.getData());
        };
    }

    private void cellClicked(Coordinate coordinate) {
        if (clickCounter == 0) {
            controller.firstCellClicked(coordinate);
            clickCounter = 1;
        } else {
            controller.cellClicked(coordinate);
        }
    }

    private MouseAdapter createMouseAdapter() {
        return new MouseAdapter() {
            boolean pressed;

            @Override
            public void mousePressed(MouseEvent e) {
                flaggedButton = (SpriteButton) e.getSource();
                flaggedButton.getModel().setArmed(true);
                flaggedButton.getModel().setPressed(true);
                pressed = true;
                if (SwingUtilities.isLeftMouseButton(e)) {
                    eventBus.publish(new CellPressedEvent());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                flaggedButton = (SpriteButton) e.getSource();
                flaggedButton.getModel().setArmed(false);
                flaggedButton.getModel().setPressed(false);
                if (pressed) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        controller.rightMouseClicked(flaggedButton.getData());
                    } else if (SwingUtilities.isLeftMouseButton(e)) {
                        eventBus.publish(new CellReleasedEvent());
                    }
                }
                pressed = false;
            }
        };
    }

    @Override
    public void setFlagOnSelectedButton() {
        if (flaggedButton == null) {
            return;
        }
        if (flaggedButton.getImageName() == null) {
            flaggedButton.setImage(flagImage, flagImageName);
            flaggedButton.removeActionListener(actionListener);
        } else {
            if (flaggedButton.getImageName().equals(flagImageName)) {
                flaggedButton.setImage(blockImage, blockImageName);
                flaggedButton.addActionListener(actionListener);
            } else {
                flaggedButton.setImage(flagImage, flagImageName);
                flaggedButton.removeActionListener(actionListener);
            }
        }
    }
}
