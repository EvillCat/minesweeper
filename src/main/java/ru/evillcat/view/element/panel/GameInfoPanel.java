package ru.evillcat.view.element.panel;

import ru.evillcat.view.element.face.ButtonWrapper;
import ru.evillcat.view.element.flag.FlagCounter;
import ru.evillcat.view.element.timer.ViewTimer;

import javax.swing.*;
import java.awt.*;

public class GameInfoPanel {

    private final JPanel panel;

    public GameInfoPanel(ViewTimer timer, ButtonWrapper buttonWrapper, FlagCounter flagCounter) {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        panel.add(timer.getTimer());
        panel.add(buttonWrapper.getFaceButton());
        panel.add(flagCounter.getFlagsCountPanel());
    }

    public JPanel getPanel() {
        return panel;
    }

}
