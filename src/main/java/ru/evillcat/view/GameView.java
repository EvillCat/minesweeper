package ru.evillcat.view;

import ru.evillcat.common.coordinate.Coordinate;
import ru.evillcat.common.mode.GameMode;

import javax.swing.*;
import java.awt.*;

public interface GameView {

    void setDefaultFlagImage(Image image);

    void setDefaultBlockImage(Image image);

    void setDefaultFlagImageName(String imageName);

    void setDefaultBlockImageName(String imageName);

    void onViewStart();

    void drawView(GameMode defaultGameModes);

    void changeChosenGameMode(GameMode gameMode);

    void reDrawField(GameMode gameMode);

    void updateFlagCount(int flagCount);

    void setButtonImage(Coordinate coordinate, Image image, String imageName);

    void deactivateAllButtonsOnField();

    void gameLost();

    void gameWon();

    void onScoresRequest();

    JFrame getFrame();

    void needDefaultGameModes();

    void setFlagOnSelectedButton();

}
