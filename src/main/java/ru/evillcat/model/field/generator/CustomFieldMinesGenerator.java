package ru.evillcat.model.field.generator;

import ru.evillcat.common.coordinate.Coordinate;

public class CustomFieldMinesGenerator extends RegionsMinesGenerator {

    public CustomFieldMinesGenerator(int heightLength, int widthLength, int minesCount, Coordinate ignoredCoordinate) {
        super(heightLength, widthLength, minesCount, ignoredCoordinate);
    }

    @Override
    protected void createRegions(int heightLength, int widthLength) {
        int height, width, length;
        if (heightLength == 1) {
            length = widthLength - 1;
            regions.add(new Region(0, 0, 0, length));
        } else if (heightLength == widthLength) {
            height = heightLength / 2;
            width = heightLength - height;
            length = heightLength - 1;
            regions.add(new Region(0, height, 0, width));
            regions.add(new Region(0, height, width, length));
            regions.add(new Region(height, length, 0, width));
            regions.add(new Region(height, length, width, length));
        } else {
            height = heightLength / 2;
            int height2 = heightLength - height;
            width = widthLength / 2;
            int width2 = widthLength - width;
            regions.add(new Region(0, height, 0, width));
            regions.add(new Region(0, height, width2, widthLength - 1));
            regions.add(new Region(height2, heightLength - 1, 0, width));
            regions.add(new Region(height2, heightLength - 1, width2, widthLength - 1));
        }

    }
}
