package ru.evillcat.context.view.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FaceImage {

    private static final Logger logger = LoggerFactory.getLogger(FaceImage.class);

    private static final String NORMAL = "normal.png";
    private static final String NORMAL_PRESSED = "normal_pressed.png";
    private static final String SCARED = "scared.png";
    private static final String WON = "won.png";
    private static final String DEAD = "dead.png";

    private static final String PATH = "/sprites/faces/";

    private static BufferedImage normalFaceImg;
    private static BufferedImage normalPressedFaceImg;
    private static BufferedImage scaredFaceImg;
    private static BufferedImage wonFaceImg;
    private static BufferedImage deadFaceImg;

    static {
        try {
            normalFaceImg = ImageIO.read(FaceImage.class.getResource(PATH + NORMAL));
            normalPressedFaceImg = ImageIO.read(FaceImage.class.getResource(PATH + NORMAL_PRESSED));
            scaredFaceImg = ImageIO.read(FaceImage.class.getResource(PATH + SCARED));
            wonFaceImg = ImageIO.read(FaceImage.class.getResource(PATH + WON));
            deadFaceImg = ImageIO.read(FaceImage.class.getResource(PATH + DEAD));
        } catch (IOException e) {
            logger.error("Ошибка загрузки изображения.", e);
            System.exit(1);
        }
    }

    public static BufferedImage getNormalFaceImg() {
        return normalFaceImg;
    }

    public static BufferedImage getNormalPressedFaceImg() {
        return normalPressedFaceImg;
    }

    public static BufferedImage getScaredFaceImg() {
        return scaredFaceImg;
    }

    public static BufferedImage getWonFaceImg() {
        return wonFaceImg;
    }

    public static BufferedImage getDeadFaceImg() {
        return deadFaceImg;
    }
}
