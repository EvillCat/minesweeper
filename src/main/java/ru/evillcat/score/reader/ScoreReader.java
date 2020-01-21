package ru.evillcat.score.reader;

import java.util.List;

public interface ScoreReader<T> {

    List<T> readScores();

}
