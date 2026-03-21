package ZombieSim.tiles;

import ZombieSim.Entity;
import java.awt.*;

/**
 * WallTile represents an impassable barrier.
 *
 * Behavior:
 * - No entity can enter
 * - Acts as a solid obstacle in the map
 */
public class WallTile extends MapTile {

    /**
     * Constructs a wall tile at a given position.
     *
     * @param p tile coordinate
     */
    public WallTile(Point p) {
        super(p);
    }

    /**
     * Prevents all entities from entering.
     */
    @Override
    public boolean canEnter(Entity e) {
        return false;
    }

    /**
     * Returns the color used to render walls.
     */
    @Override
    public Color getColor() {
        return Color.DARK_GRAY;
    }
}
