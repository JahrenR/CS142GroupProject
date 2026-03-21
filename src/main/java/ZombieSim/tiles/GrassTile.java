package ZombieSim.tiles;

import ZombieSim.Entity;
import java.awt.*;

/**
 * GrassTile represents normal terrain.
 *
 * Behavior:
 * - Any entity can enter
 * - Only one entity allowed at a time
 * - Default movement tile
 */

public class GrassTile extends MapTile {

    /**
     * Constructs a grass tile at a given position.
     *
     * @param p tile coordinate
     */
    public GrassTile(Point p) {
        super(p);
    }

    /**
     * Allows entry only if the tile is not already occupied.
     */
    @Override
    public boolean canEnter(Entity e) {
        return entity == null;
    }

    /**
     * Returns the color used to render grass.
     */
    @Override
    public Color getColor() {
        return new Color(55, 140, 55);
    }
}
