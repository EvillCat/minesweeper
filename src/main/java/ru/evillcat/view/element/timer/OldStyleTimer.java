package ru.evillcat.view.element.timer;

import ru.evillcat.context.view.image.NumberImage;

import javax.swing.*;
import java.awt.*;

public class OldStyleTimer implements ViewTimer {

    private final JPanel timerPanel;
    private final JLabel hundredLabel;
    private final JLabel dozenLabel;
    private final JLabel unitsLabel;
    private final Image defaultImage;

    public OldStyleTimer() {
        hundredLabel = new JLabel();
        dozenLabel = new JLabel();
        unitsLabel = new JLabel();

        hundredLabel.setPreferredSize(new Dimension(21, 40));
        dozenLabel.setPreferredSize(new Dimension(21, 40));
        unitsLabel.setPreferredSize(new Dimension(21, 40));

        defaultImage = NumberImage.createImage(0);

        timerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        timerPanel.add(hundredLabel);
        timerPanel.add(dozenLabel);
        timerPanel.add(unitsLabel);
        setDefaultState();
    }

    @Override
    public JPanel getTimer() {
        return timerPanel;
    }

    @Override
    public void setDefaultState() {
        hundredLabel.setIcon(new ImageIcon(defaultImage));
        dozenLabel.setIcon(new ImageIcon(defaultImage));
        unitsLabel.setIcon(new ImageIcon(defaultImage));
    }

    @Override
    public void setFirstSecondImage(Image image) {
        unitsLabel.setIcon(new ImageIcon(image));
    }

    @Override
    public void setSecondSecondImage(Image image) {
        dozenLabel.setIcon(new ImageIcon(image));
    }

    @Override
    public void setThirdSecondImage(Image image) {
        hundredLabel.setIcon(new ImageIcon(image));
    }
}
