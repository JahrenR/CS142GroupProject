package ZombieSim.tiles;

import ZombieSim.Entity;
import java.awt.*;
import java.awt.Point;

/**
 * MapTile is the abstract base class representing a single tile on the simulation grid.
 *
 * Each tile:
 * - Has a fixed (x, y) coordinate
 * - May contain at most one Entity
 * - Defines rules for whether an Entity can enter it
 * - Defines how it should be visually represented (color)
 *
 * Subclasses implement specific terrain behavior such as grass, water, walls, etc.
 */

public abstract class MapTile {
    protected final Point p; // immutable coordinate of this tile
    protected Entity entity; // entity currently occupying this tile (null if empty)

    /**
     * Constructs a tile at a given coordinate.
     *
     * @param p the position of the tile
     */

    public MapTile(Point p) {
        this.p = new Point(p);
        this.entity = null; // tiles start empty

    }

    /**
     * Determines whether a given entity is allowed to enter this tile.
     *
     * @param e the entity attempting to enter
     * @return true if entry is allowed, false otherwise
     */
    public abstract boolean canEnter(Entity e);

    /**
     * Returns the display color of this tile for rendering in the GUI.
     *
     * @return the tile color
     */
    public abstract Color getColor();

    /**
     * Returns the entity currently on this tile.
     *
     * @return the entity, or null if empty
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Sets or removes the entity on this tile.
     *
     * @param e the entity to place (or null to clear)
     */
    public void setEntity(Entity e) {
        this.entity = e;
    }

    /**
     * Returns a copy of the tile's position.
     *
     * @return point representing tile coordinates
     */
    public Point getPoint() {
        return new Point(p);
    }

    /** @return x-coordinate */
    public int getX() {
        return p.x;
    }

    /** @return y-coordinate */
    public int getY() {
        return p.y;
    }
}

