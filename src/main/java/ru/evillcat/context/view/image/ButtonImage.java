package ru.evillcat.context.view.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ButtonImage {

    private static final Logger logger = LoggerFactory.getLogger(ButtonImage.class);

    public static final String BLOCK = "block.png";
    public static final String ONE = "1.png";
    public static final String TWO = "2.png";
    public static final String THREE = "3.png";
    public static final String FOUR = "4.png";
    public static final String FIVE = "5.png";
    public static final String SIX = "6.png";
    public static final String SEVEN = "7.png";
    public static final String EIGHT = "8.png";
    public static final String EMPTY = "empty.png";
    public static final String FLAG = "flag.png";
    public static final String MINE = "mine.png";
    private static final String PATH = "/sprites/buttons/";

    private static BufferedImage blockImg;
    private static BufferedImage oneImg;
    private static BufferedImage twoImg;
    private static BufferedImage threeImg;
    private static BufferedImage fourImg;
    private static BufferedImage fiveImg;
    private static BufferedImage sixImg;
    private static BufferedImage sevenImg;
    private static BufferedImage eightImg;
    private static BufferedImage emptyImg;
    private static BufferedImage flagImg;
    private static BufferedImage mineImg;

    static {
        try {
            blockImg = ImageIO.read(ButtonImage.class.getResource(PATH + BLOCK));
            oneImg = ImageIO.read(ButtonImage.class.getResource(PATH + ONE));
            twoImg = ImageIO.read(ButtonImage.class.getResource(PATH + TWO));
            threeImg = ImageIO.read(ButtonImage.class.getResource(PATH + THREE));
            fourImg = ImageIO.read(ButtonImage.class.getResource(PATH + FOUR));
            fiveImg = ImageIO.read(ButtonImage.class.getResource(PATH + FIVE));
            sixImg = ImageIO.read(ButtonImage.class.getResource(PATH + SIX));
            sevenImg = ImageIO.read(ButtonImage.class.getResource(PATH + SEVEN));
            eightImg = ImageIO.read(ButtonImage.class.getResource(PATH + EIGHT));
            emptyImg = ImageIO.read(ButtonImage.class.getResource(PATH + EMPTY));
            flagImg = ImageIO.read(ButtonImage.class.getResource(PATH + FLAG));
            mineImg = ImageIO.read(ButtonImage.class.getResource(PATH + MINE));
        } catch (IOException e) {
            logger.error("Ошибка загрузки изображения.", e);
            System.exit(1);
        }
    }

    public static BufferedImage getDefaultBlock() {
        return blockImg;
    }

    public static BufferedImage getImageByNumber(int number) {
        switch (number) {
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
            default:
                return emptyImg;
        }
    }

    public static BufferedImage getEmptyImage() {
        return emptyImg;
    }

    public static BufferedImage getFlagImage() {
        return flagImg;
    }

    public static BufferedImage getMineImage() {
        return mineImg;
    }

    public static String getImageName(int number) {
        switch (number) {
            case 1:
                return ONE;
            case 2:
                return TWO;
            case 3:
                return THREE;
            case 4:
                return FOUR;
            case 5:
                return FIVE;
            case 6:
                return SIX;
            case 7:
                return SEVEN;
            case 8:
                return EIGHT;
            default:
                return BLOCK;
        }
    }
}
