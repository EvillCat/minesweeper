package ru.evillcat.view.custom.button;

import ru.evillcat.common.coordinate.Coordinate;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class SpriteButton extends JButtonWithData<Coordinate> {

    private static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);

    private Image imgUp;
    private Image imgDown;
    private boolean buttonDown;
    private String imageName;

    public SpriteButton(Image sprite, int h, int w) {
        this.data = new Coordinate(h, w);
        setFocusable(false);
        setBackground(TRANSPARENT_COLOR);

        int buttonWidth = sprite.getWidth(null);
        int buttonHeight = sprite.getHeight(null);
        setSize(buttonWidth, buttonHeight);

        imgUp = new BufferedImage(buttonWidth, buttonHeight, BufferedImage.TYPE_INT_ARGB);
        imgDown = new BufferedImage(buttonWidth, buttonHeight, BufferedImage.TYPE_INT_ARGB);
        imgUp.getGraphics().drawImage(sprite, 0, 0, buttonWidth, buttonHeight,
                0, 0, buttonWidth, buttonHeight, null);
        imgDown.getGraphics().drawImage(sprite, 0, 0, buttonWidth, buttonHeight,
                buttonWidth, 0, buttonWidth * 2, buttonHeight, null);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                setButtonDown(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setButtonDown(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setButtonDown(false);
            }
        });
    }

    protected void setButtonDown(boolean newValue) {
        if (buttonDown != newValue) {
            buttonDown = newValue;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        getParent().repaint(getX(), getY(), getX() + getWidth(), getY() + getHeight());
        g.drawImage(buttonDown ? imgDown : imgUp, 0, 0, null);
    }

    public void setImage(Image sprite, String imageName) {
        this.imageName = imageName;
        int buttonWidth = sprite.getWidth(null);
        int buttonHeight = sprite.getHeight(null);
        setSize(buttonWidth, buttonHeight);
        imgUp = new BufferedImage(buttonWidth, buttonHeight, BufferedImage.TYPE_INT_ARGB);
        imgDown = new BufferedImage(buttonWidth, buttonHeight, BufferedImage.TYPE_INT_ARGB);
        imgUp.getGraphics().drawImage(sprite, 0, 0, buttonWidth, buttonHeight,
                0, 0, buttonWidth, buttonHeight, null);
        imgDown.getGraphics().drawImage(sprite, 0, 0, buttonWidth, buttonHeight,
                buttonWidth, 0, buttonWidth * 2, buttonHeight, null);
    }

    @Override
    public Coordinate getData() {
        return this.data;
    }

    public String getImageName() {
        return imageName;
    }
}
