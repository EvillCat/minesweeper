package ru.evillcat.score.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.evillcat.score.reader.ScoreReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ScoreFileWriter implements ScoreWriter {

    private static final Logger logger = LoggerFactory.getLogger(ScoreFileWriter.class);
    private static final String X = "x";
    private static final String SPACE = " ";
    private static final String MINES = " mines";
    private static final String SECONDS = " seconds";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private final String path;
    private final ScoreReader<String> reader;

    public ScoreFileWriter(String path, ScoreReader<String> reader) {
        this.path = path;
        this.reader = reader;
    }

    @Override
    public void save(int height, int width, int mines, int seconds) {
        StringBuilder sb = new StringBuilder();
        sb.append(height).append(X).append(width);
        List<String> scores = reader.readScores();
        String score = null;
        if (scores.size() != 0) {
            for (String scoreFromFile : scores) {
                if (scoreFromFile.contains(sb.toString())) {
                    score = scoreFromFile;
                }
            }
            if (score != null) {
                int indexOfMinesString = score.indexOf(MINES) + MINES.length();
                int indexOfSecondsString = score.indexOf(SECONDS);
                String secondsResult = score.substring(indexOfMinesString, indexOfSecondsString).trim();
                int previousSeconds = Integer.parseInt(secondsResult);
                if (previousSeconds > seconds) {
                    write(sb, mines, seconds);
                }
            }
        } else {
            write(sb, mines, seconds);
        }
    }

    private void write(StringBuilder sb, int mines, int seconds) {
        sb.append(SPACE).append(mines).append(MINES).append(SPACE)
                .append(seconds).append(SECONDS).append(LINE_SEPARATOR);
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8))) {

            writer.write(sb.toString());
        } catch (IOException ex) {
            logger.error("Couldn't write score.");
            logger.error(ex.toString());
        }
    }
}
