package ru.evillcat.model.field.generator;

import ru.evillcat.common.coordinate.Coordinate;

import java.util.*;

/**
 * Класс используется для генерации мин на поле.
 * Концепция заключается в разделении поля на регионы и генерации для каждого из них определенного количества мин.
 * Разделение на регионы - ответственность наследника,
 * данный здесь функционал отвечает лишь за распределение мин по регионам.
 * Максимальное количество мин в регионе составляет 0.4 от общего количества.
 */
public abstract class RegionsMinesGenerator {

    private static final double PERCENT_REGION_MAX_MINES_LIMIT = 0.4;

    protected final List<Region> regions;
    protected final int regionMaxMinesCount;
    protected final Coordinate ignoredCoordinate;
    protected final Set<Coordinate> coordinatesSet;
    protected int totalMinesCount;

    public RegionsMinesGenerator(int heightLength, int widthLength, int minesCount, Coordinate ignoredCoordinate) {
        this.totalMinesCount = minesCount;
        this.ignoredCoordinate = ignoredCoordinate;
        regions = new ArrayList<>();
        coordinatesSet = new HashSet<>();
        createRegions(heightLength, widthLength);
        regionMaxMinesCount = heightLength == 0 ? minesCount : (int) (minesCount * PERCENT_REGION_MAX_MINES_LIMIT);
    }

    public List<Coordinate> createMinesCoordinates() {
        distributeMinesToRegions();
        for (Region region : regions) {
            region.createMinesCoordinates();
        }
        return new ArrayList<>(coordinatesSet);
    }

    protected abstract void createRegions(int heightLength, int widthLength);

    protected void distributeMinesToRegions() {
        Random random = new Random();
        if (regions.size() == 1) {
            regions.get(0).setRegionMinesCount(totalMinesCount);
        } else {
            while (totalMinesCount > 0) {
                for (Region region : regions) {
                    if (region.regionMinesCount < regionMaxMinesCount) {
                        int minesRequired = regionMaxMinesCount - region.regionMinesCount;
                        int createdMines = random.nextInt(compareMinesTotalCountAndRequired(minesRequired) + 1);
                        region.setRegionMinesCount((region.getRegionMinesCount() + createdMines));
                        totalMinesCount -= createdMines;
                    }
                }
            }
        }
    }

    protected int compareMinesTotalCountAndRequired(int minesRequired) {
        return Math.min(totalMinesCount, minesRequired);
    }

    protected class Region {

        final int beginHeight;
        final int beginWidth;
        final int endHeight;
        final int endWidth;
        int regionMinesCount;

        Region(int beginHeight, int endHeight, int beginWidth, int endWidth) {
            this.beginHeight = beginHeight;
            this.beginWidth = beginWidth;
            this.endHeight = endHeight;
            this.endWidth = endWidth;
        }

        void createMinesCoordinates() {
            Random random = new Random();
            Coordinate coordinate;
            while (regionMinesCount != 0) {

                int h = beginHeight + random.nextInt(endHeight - beginHeight + 1);
                int w = beginWidth + random.nextInt(endWidth - beginWidth + 1);
                coordinate = new Coordinate(h, w);

                if (!ignoredCoordinate.equals(coordinate)) {
                    if (coordinatesSet.add(coordinate)) {
                        --regionMinesCount;
                    }
                }
            }
        }

        protected int getRegionMinesCount() {
            return regionMinesCount;
        }

        protected void setRegionMinesCount(int regionMinesCount) {
            this.regionMinesCount = regionMinesCount;
        }
    }
}
