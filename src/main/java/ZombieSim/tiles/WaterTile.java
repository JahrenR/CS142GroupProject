package ZombieSim.tiles;

import ZombieSim.Entity;

import java.awt.*;

/**
 * WaterTile represents water terrain.
 *
 * Behavior:
 * - All entities are allowed to enter
 * - Special effects handled elsewhere (e.g., zombies drown in SimModel)
 * - Movement may be slowed (handled in Entity class)
 */
public class WaterTile extends MapTile {

    /**
     * Constructs a water tile at a given position.
     *
     * @param p tile coordinate
     */
    public WaterTile(Point p) {
        super(p);
    }

    /**
     * Allows all entities to enter water.
     * (Consequences like drowning are handled in the model)
     */
    @Override
    public boolean canEnter(Entity e) {
        return true;
    }

    /**
     * Returns the color used to render water.
     */
    @Override
    public Color getColor() {
        return new Color(70,130,180);
    }
}
