package ru.evillcat.view.element.face;

import ru.evillcat.context.view.image.FaceImage;
import ru.evillcat.event.bus.EventBus;
import ru.evillcat.event.bus.EventListener;
import ru.evillcat.event.minesweeper.type.*;
import ru.evillcat.view.custom.button.DataJButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FaceButtonWrapper implements ButtonWrapper {

    private final DataJButton faceButton;

    public FaceButtonWrapper(EventBus eventBus) {
        faceButton = new DataJButton();
        faceButton.setIcon(new ImageIcon(FaceImage.getNormalFaceImg()));
        faceButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                faceButton.setIcon(new ImageIcon(FaceImage.getNormalPressedFaceImg()));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    faceButton.setIcon(new ImageIcon(FaceImage.getNormalFaceImg()));
                    eventBus.publish(new TimerStopEvent());
                    eventBus.publish(new RecreateFieldEvent());
                    faceButton.openForDefaultIconPairOperations();
                }
            }

        });
        faceButton.setPreferredSize(new Dimension(40, 40));
        faceButton.setMaximumSize(new Dimension(40, 40));

        eventBus.subscribe(CellPressedEvent.class, new OnCellClickListener());
        eventBus.subscribe(CellReleasedEvent.class, new OnCellReleasedListener());
        eventBus.subscribe(FieldCreateEvent.class, new RefreshFaceButtonState());
        eventBus.subscribe(GameLostEvent.class, new OnLostListener());
        eventBus.subscribe(GameWonEvent.class, new OnWonListener());
    }

    @Override
    public JButton getFaceButton() {
        return faceButton;
    }


    private class OnCellClickListener implements EventListener<CellPressedEvent> {

        @Override
        public void update(CellPressedEvent event) {
            if (faceButton.isClosed()) {
                faceButton.setIcon(new ImageIcon(FaceImage.getScaredFaceImg()));
            }
        }
    }

    private class OnCellReleasedListener implements EventListener<CellReleasedEvent> {

        @Override
        public void update(CellReleasedEvent event) {
            if (faceButton.isClosed()) {
                faceButton.setIcon(new ImageIcon(FaceImage.getNormalFaceImg()));
            }
        }
    }

    private class RefreshFaceButtonState implements EventListener<FieldCreateEvent> {

        @Override
        public void update(FieldCreateEvent event) {
            faceButton.openForDefaultIconPairOperations();
        }
    }

    private class OnLostListener implements EventListener<GameLostEvent> {

        @Override
        public void update(GameLostEvent event) {
            faceButton.closeForDefaultIconPairOperations();
            faceButton.setIcon(new ImageIcon(FaceImage.getDeadFaceImg()));
        }
    }

    private class OnWonListener implements EventListener<GameWonEvent> {

        @Override
        public void update(GameWonEvent event) {
            faceButton.closeForDefaultIconPairOperations();
            faceButton.setIcon(new ImageIcon(FaceImage.getWonFaceImg()));
        }
    }
}


