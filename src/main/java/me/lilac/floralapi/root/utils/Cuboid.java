package me.lilac.floralapi.root.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Utility class used for creating cuboids within a world.
 */
public class Cuboid {

    private Location max;
    private Location min;

    public Cuboid(Location min, Location max) {
        this.min = min;
        this.max = max;
    }

    public boolean contains(Location location) {
        return contains(location.toVector());
    }

    public boolean contains(Vector vector) {
        Vector max = this.max.toVector();
        Vector min = this.min.toVector();

        Vector worldMin = new Vector(
                Math.min(min.getX(), max.getX()),
                Math.min(min.getY(), max.getY()),
                Math.min(min.getZ(), max.getZ()));

        Vector worldMax = new Vector(
                Math.max(min.getX(), max.getX()),
                Math.max(min.getY(), max.getY()),
                Math.max(min.getZ(), max.getZ()));

        return vector.isInAABB(worldMin, worldMax);
    }

    public Location getMax() {
        return max;
    }

    public Location getMin() {
        return min;
    }
}
