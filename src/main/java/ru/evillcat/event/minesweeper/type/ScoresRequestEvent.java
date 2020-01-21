package ru.evillcat.event.minesweeper.type;

import ru.evillcat.event.bus.Event;

import java.util.List;

public class ScoresRequestEvent extends Event {

    private final List<String> scores;


    public ScoresRequestEvent(List<String> scores) {
        this.scores = scores;
    }

    public List<String> getScores() {
        return scores;
    }
}
