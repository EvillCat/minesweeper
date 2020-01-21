package ru.evillcat.score;

import ru.evillcat.score.reader.ScoreReader;
import ru.evillcat.score.writer.ScoreWriter;

import java.util.List;

public class FileSystemScoreSaver implements ScoreSaver<String> {

    private final ScoreWriter writer;
    private final ScoreReader<String> reader;

    public FileSystemScoreSaver(ScoreWriter writer, ScoreReader<String> reader) {
        this.writer = writer;
        this.reader = reader;
    }

    @Override
    public void write(int height, int width, int mines, int seconds) {
        writer.save(height, width, mines, seconds);
    }

    @Override
    public List<String> getScores() {
        return reader.readScores();
    }
}
