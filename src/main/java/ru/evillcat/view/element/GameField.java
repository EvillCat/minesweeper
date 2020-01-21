package ru.evillcat.view.element;

import ru.evillcat.common.coordinate.Coordinate;
import ru.evillcat.event.bus.EventBus;
import ru.evillcat.event.minesweeper.type.OpenNearMarkedCellsEvent;
import ru.evillcat.view.custom.button.SpriteButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GameField {

    private final MouseAdapter mouseAdapter;
    private final ActionListener actionListener;
    private final EventBus eventBus;

    private final List<SpriteButton> buttonList;
    private final JScrollPane scrollPane;

    public GameField(int height, int width, ActionListener actionListener, MouseAdapter mouseAdapter,
                     EventBus eventBus, Image defaultBlockImage) {
        this.mouseAdapter = mouseAdapter;
        this.actionListener = actionListener;
        this.eventBus = eventBus;
        buttonList = new ArrayList<>();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(height, width));
        jPanel.setPreferredSize(new Dimension((30 * width), (30 * height)));
        jPanel.setMaximumSize(new Dimension(40 * 30, 25 * 30));

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                SpriteButton button = new SpriteButton(defaultBlockImage, i, j);
                button.addActionListener(actionListener);
                button.addMouseListener(mouseAdapter);
                buttonList.add(button);
                jPanel.add(button);
            }
        }
        scrollPane = new JScrollPane(jPanel);
        int scrollPaneHeight = (30 * height) + 3;
        int scrollPaneWidth = (30 * width) + 3;
        scrollPane.setPreferredSize(new Dimension(scrollPaneWidth, scrollPaneHeight));


        if (height >= 25 || width >= 50) {

            if (height >= 25) {
                scrollPaneHeight = 500;
            }
            if (width >= 50) {
                scrollPaneWidth = 800;
            }
            scrollPane.setPreferredSize(new Dimension(scrollPaneWidth, scrollPaneHeight));
            scrollPane.setMaximumSize(new Dimension(scrollPaneWidth, scrollPaneHeight));
            scrollPane.setMinimumSize(new Dimension(scrollPaneWidth, scrollPaneHeight));
        }
    }

    public JScrollPane getJPanel() {
        return scrollPane;
    }

    public void setButtonImage(Coordinate coordinate, Image image, String imageName) {
        for (SpriteButton button : buttonList) {
            if (button.getData().equals(coordinate)) {
                button.setImage(image, imageName);
                button.removeMouseListener(mouseAdapter);
                button.addMouseListener(createMouseAdapter());
                button.setEnabled(false);
                break;
            }
        }
    }

    public void deactivateAllButtons() {
        for (SpriteButton button : buttonList) {
            button.removeMouseListener(mouseAdapter);
            button.removeActionListener(actionListener);
        }
    }

    private MouseAdapter createMouseAdapter() {
        return new MouseAdapter() {
            SpriteButton button;
            boolean leftPressed;
            boolean rightPressed;
            boolean wheelPressed;

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    rightPressed = true;
                }
                if (SwingUtilities.isLeftMouseButton(e)) {
                    leftPressed = true;
                }
                if (SwingUtilities.isMiddleMouseButton(e)) {
                    wheelPressed = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button = (SpriteButton) e.getSource();
                if (wheelPressed) {
                    eventBus.publish(new OpenNearMarkedCellsEvent(button.getData()));
                } else if (leftPressed && rightPressed) {
                    eventBus.publish(new OpenNearMarkedCellsEvent(button.getData()));
                } else if (leftPressed) {
                    eventBus.publish(new OpenNearMarkedCellsEvent(button.getData()));
                }
                leftPressed = false;
                rightPressed = false;
                wheelPressed = false;
            }
        };
    }
}
