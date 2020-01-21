package ru.evillcat.common.coordinate;

import java.util.Objects;

public class Coordinate {

    private final int height;
    private final int width;

    public Coordinate(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return height == that.height &&
                width == that.width;
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width);
    }

    @Override
    public String toString() {
        return "[" + height + ", " + width + "]";
    }
}
