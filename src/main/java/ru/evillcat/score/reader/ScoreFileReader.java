package ru.evillcat.score.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ScoreFileReader implements ScoreReader<String> {

    private static final Logger logger = LoggerFactory.getLogger(ScoreFileReader.class);
    private final String path;

    public ScoreFileReader(String path) {
        this.path = path;
    }

    @Override
    public List<String> readScores() {
        List<String> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
            String str;
            while ((str = reader.readLine()) != null) {
                scores.add(str);
            }
        } catch (IOException ex) {
            logger.error("Couldn't read scores file.");
            logger.error(ex.toString());
        }
        return scores;
    }
}
