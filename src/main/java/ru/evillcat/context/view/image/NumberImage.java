package ru.evillcat.context.view.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class NumberImage {

    private static final Logger logger = LoggerFactory.getLogger(NumberImage.class);

    private static final String ZERO = "0.png";
    private static final String ONE = "1.png";
    private static final String TWO = "2.png";
    private static final String THREE = "3.png";
    private static final String FOUR = "4.png";
    private static final String FIVE = "5.png";
    private static final String SIX = "6.png";
    private static final String SEVEN = "7.png";
    private static final String EIGHT = "8.png";
    private static final String NINE = "9.png";

    private static final String PATH = "/sprites/numbers/";

    private static BufferedImage zeroImg;
    private static BufferedImage oneImg;
    private static BufferedImage twoImg;
    private static BufferedImage threeImg;
    private static BufferedImage fourImg;
    private static BufferedImage fiveImg;
    private static BufferedImage sixImg;
    private static BufferedImage sevenImg;
    private static BufferedImage eightImg;
    private static BufferedImage nineImg;

    static {
        try {
            zeroImg = ImageIO.read(NumberImage.class.getResource(PATH + ZERO));
            oneImg = ImageIO.read(NumberImage.class.getResource(PATH + ONE));
            twoImg = ImageIO.read(NumberImage.class.getResource(PATH + TWO));
            threeImg = ImageIO.read(NumberImage.class.getResource(PATH + THREE));
            fourImg = ImageIO.read(NumberImage.class.getResource(PATH + FOUR));
            fiveImg = ImageIO.read(NumberImage.class.getResource(PATH + FIVE));
            sixImg = ImageIO.read(NumberImage.class.getResource(PATH + SIX));
            sevenImg = ImageIO.read(NumberImage.class.getResource(PATH + SEVEN));
            eightImg = ImageIO.read(NumberImage.class.getResource(PATH + EIGHT));
            nineImg = ImageIO.read(NumberImage.class.getResource(PATH + NINE));
        } catch (IOException e) {
            logger.error("Ошибка загрузки изображения.", e);
            System.exit(1);
        }
    }


    public static Image createImage(int value) {
        switch (value) {
            case 1:
                return oneImg;
            case 2:
                return twoImg;
            case 3:
                return threeImg;
            case 4:
                return fourImg;
            case 5:
                return fiveImg;
            case 6:
                return sixImg;
            case 7:
                return sevenImg;
            case 8:
                return eightImg;
            case 9:
                return nineImg;
            default:
                return zeroImg;
        }
    }
}
