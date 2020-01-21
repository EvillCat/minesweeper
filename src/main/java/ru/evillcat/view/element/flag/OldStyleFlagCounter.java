package ru.evillcat.view.element.flag;

import ru.evillcat.context.view.image.NumberImage;
import ru.evillcat.event.bus.EventBus;
import ru.evillcat.event.bus.EventListener;
import ru.evillcat.event.minesweeper.type.ViewFlagUpdateEvent;

import javax.swing.*;
import java.awt.*;

public class OldStyleFlagCounter implements FlagCounter {

    private final JPanel flagsCountPanel;
    private final JLabel unitsLabel;
    private final JLabel dozenLabel;
    private final JLabel hundredLabel;
    private final JLabel thousandLabel;

    public OldStyleFlagCounter(EventBus eventBus) {
        eventBus.subscribe(ViewFlagUpdateEvent.class, new FlagSetter());

        unitsLabel = new JLabel();
        dozenLabel = new JLabel();
        hundredLabel = new JLabel();
        thousandLabel = new JLabel();

        unitsLabel.setPreferredSize(new Dimension(21, 40));
        dozenLabel.setPreferredSize(new Dimension(21, 40));
        hundredLabel.setPreferredSize(new Dimension(21, 40));
        thousandLabel.setPreferredSize(new Dimension(21, 40));
        flagsCountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        flagsCountPanel.add(thousandLabel);
        flagsCountPanel.add(hundredLabel);
        flagsCountPanel.add(dozenLabel);
        flagsCountPanel.add(unitsLabel);
    }

    @Override
    public JPanel getFlagsCountPanel() {
        return flagsCountPanel;
    }

    private void setCounterCells(int flags) {
        int thousandValue = flags / 1000;
        int hundredValue = flags / 100 % 10;
        int dozenValue = flags / 10 % 10;
        int unitValue = flags % 10;
        unitsLabel.setIcon(new ImageIcon(NumberImage.createImage(unitValue)));
        dozenLabel.setIcon(new ImageIcon(NumberImage.createImage(dozenValue)));
        hundredLabel.setIcon(new ImageIcon(NumberImage.createImage(hundredValue)));
        thousandLabel.setIcon(new ImageIcon(NumberImage.createImage(thousandValue)));
    }

    private class FlagSetter implements EventListener<ViewFlagUpdateEvent> {

        @Override
        public void update(ViewFlagUpdateEvent event) {
            setCounterCells(event.getFlagCount());
        }
    }
}
