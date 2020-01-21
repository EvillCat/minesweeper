package ru.evillcat.score;

import java.util.List;

public interface ScoreSaver<T> {

    void write(int height, int width, int mines, int seconds);

    List<T> getScores();

}
