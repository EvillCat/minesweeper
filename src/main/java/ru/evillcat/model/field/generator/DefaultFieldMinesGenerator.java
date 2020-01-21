package ru.evillcat.model.field.generator;

import ru.evillcat.common.coordinate.Coordinate;

import java.util.*;

/**
 * Располагает мины на стандартных полях размером 9 на 9, 16 на 16, 16 на 32.
 * Класс для себя делит поле на пять регионов: 4 прямоугольных по углам и один крестообразный по центру.
 */
public class DefaultFieldMinesGenerator extends RegionsMinesGenerator {

    private static final int NINE = 9;
    private static final int SIXTEEN = 16;
    private static final int THIRTY = 30;

    /**
     * Константы используются для определения координат крестообразного региона.
     * См.:
     * {@link DefaultFieldMinesGenerator#getBeginAndEndCrossRegionCoordinates(int)}
     * {@link DefaultFieldMinesGenerator#createCrossRegion(int, int)}
     */
    private static final int NINE_BEGIN_AND_END_CROSS_COORDINATE = 4;
    private static final int[] SIXTEEN_BEGIN_AND_END_CROSS_COORDINATE = new int[]{7, 8};
    private static final int[] THIRTY_BEGIN_AND_END_CROSS_COORDINATE = new int[]{13, 16};

    public DefaultFieldMinesGenerator(int heightLength, int widthLength, int minesCount, Coordinate ignoredCoordinate) {
        super(heightLength, widthLength, minesCount, ignoredCoordinate);

        createCrossRegion(heightLength, widthLength);
    }

    private void createCrossRegion(int heightSideLength, int widthSideLength) {
        int[] heightCoordinates = getBeginAndEndCrossRegionCoordinates(heightSideLength);

        int[] widthCoordinates = getBeginAndEndCrossRegionCoordinates(widthSideLength);

        regions.add(new CrossCenterRegion(heightCoordinates[0], heightCoordinates[1], widthCoordinates[0],
                widthCoordinates[1], widthSideLength, heightSideLength));
    }

    @Override
    protected void createRegions(int heightLength, int widthLength) {
        int[] beginAndEndHeight = getBeginAndEndCrossRegionCoordinates(heightLength);
        int[] beginAndEndWidth = getBeginAndEndCrossRegionCoordinates(widthLength);
        regions.add(
                new Region(0, beginAndEndHeight[0] - 1,
                        0, beginAndEndWidth[0] - 1));
        regions.add(
                new Region(0, beginAndEndHeight[0] - 1,
                        beginAndEndWidth[1] + 1, widthLength - 1));
        regions.add(
                new Region(beginAndEndHeight[1] + 1, heightLength - 1,
                        0, beginAndEndWidth[0] - 1));
        regions.add(
                new Region(beginAndEndHeight[1] + 1, heightLength - 1,
                        beginAndEndWidth[1] + 1, widthLength - 1));
    }

    private int[] getBeginAndEndCrossRegionCoordinates(int sideLength) {
        switch (sideLength) {
            case SIXTEEN:
                return SIXTEEN_BEGIN_AND_END_CROSS_COORDINATE;
            case THIRTY:
                return THIRTY_BEGIN_AND_END_CROSS_COORDINATE;
            case NINE:
            default:
                return new int[]{NINE_BEGIN_AND_END_CROSS_COORDINATE, NINE_BEGIN_AND_END_CROSS_COORDINATE};
        }
    }

    private class CrossCenterRegion extends Region {

        final int subArrayLength;
        final int arrayLength;

        CrossCenterRegion(int beginHeight, int endHeight, int beginWidth, int endWidth,
                          int subArrayLength, int arrayLength) {
            super(beginHeight, endHeight, beginWidth, endWidth);
            this.subArrayLength = subArrayLength;
            this.arrayLength = arrayLength;
        }

        /**
         * Располагает мины либо в горизонтальной, либо в вертикальной плоскости крестообразного региона.
         * В горизонтальной плоскости высота(h) ограничена пределами beginHigh <= h <= endHigh,
         * а ширина(w) ограничена 0 <= w <= array[0].length.
         * В вертикальной плоскости высота(h) ограничена пределами 0 <= h <= array.length,
         * а ширина(w) ограничена beginWidth <= w <= endWidth.
         */
        @Override
        public void createMinesCoordinates() {
            Random random = new Random();
            Coordinate coordinate;

            while (regionMinesCount != 0) {
                if (random.nextBoolean()) {
                    coordinate = createMineOnHorizontal();
                } else {
                    coordinate = createMineOnVertical();
                }
                if (!ignoredCoordinate.equals(coordinate)) {
                    if (coordinatesSet.add(coordinate)) {
                        --regionMinesCount;
                    }
                }
            }
        }

        private Coordinate createMineOnHorizontal() {
            Random random = new Random();
            int width = random.nextInt(subArrayLength);
            int height;
            if (beginHeight == endHeight) {
                height = beginHeight;
            } else {
                height = random.nextInt((endHeight - beginHeight) + 1);
            }
            return new Coordinate(height, width);
        }

        private Coordinate createMineOnVertical() {
            Random random = new Random();
            int w;
            int h = random.nextInt(arrayLength);
            if (beginWidth == endWidth) {
                w = beginWidth;
            } else {
                w = random.nextInt((endWidth - beginWidth) + 1);
            }
            return new Coordinate(h, w);
        }
    }
}
