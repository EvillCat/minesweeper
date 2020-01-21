package ru.evillcat.view.element.timer;

import javax.swing.*;
import java.awt.*;

public interface ViewTimer {

    JPanel getTimer();

    void setDefaultState();

    void setFirstSecondImage(Image image);

    void setSecondSecondImage(Image image);

    void setThirdSecondImage(Image image);
}
