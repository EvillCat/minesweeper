package ru.evillcat.model.field;

import ru.evillcat.common.coordinate.Coordinate;
import ru.evillcat.common.mode.GameMode;
import ru.evillcat.event.bus.EventBus;
import ru.evillcat.event.minesweeper.type.DefaultModeRequiredEvent;
import ru.evillcat.event.minesweeper.type.DefaultModesInformationNeedEvent;
import ru.evillcat.event.minesweeper.type.FieldCreateEvent;
import ru.evillcat.model.field.generator.CustomFieldMinesGenerator;
import ru.evillcat.model.field.generator.DefaultFieldMinesGenerator;
import ru.evillcat.model.field.generator.RegionsMinesGenerator;

import java.util.ArrayList;
import java.util.List;

public class FieldGenerator {

    private final EventBus eventBus;
    private final List<GameMode> defaultModes;

    public FieldGenerator(EventBus eventBus) {
        this.eventBus = eventBus;
        defaultModes = new ArrayList<>();
        defaultModes.add(new GameMode(9, 9, 10));
        defaultModes.add(new GameMode(16, 16, 40));
        defaultModes.add(new GameMode(16, 30, 99));
    }

    public void loadDefaultModes() {
        eventBus.publish(new DefaultModeRequiredEvent(defaultModes.get(0)));
    }

    public FieldService generateField(int height, int width, int minesCount, Coordinate ignoredCoordinates) {
        GameMode gameMode = new GameMode(height, width, minesCount);
        RegionsMinesGenerator minesGenerator;
        if (defaultModes.contains(gameMode)) {
            minesGenerator = new DefaultFieldMinesGenerator(height, width, minesCount, ignoredCoordinates);
        } else {
            minesGenerator = new CustomFieldMinesGenerator(height, width, minesCount, ignoredCoordinates);
        }
        eventBus.publish(new FieldCreateEvent(height, width, minesCount));
        return new FieldService(gameMode, minesGenerator, eventBus);
    }

    public void sendDefaultModeInformation() {
        eventBus.publish(new DefaultModesInformationNeedEvent(defaultModes));
    }
}
