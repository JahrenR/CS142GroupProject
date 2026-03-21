package ZombieSim.tiles;

import ZombieSim.Entity;
import ZombieSim.Unit;
import java.awt.*;

/**
 * SafeZoneTile represents a protected area for humans.
 *
 * Behavior:
 * - Zombies are NOT allowed to enter
 * - Other entities can enter only if tile is empty
 * - Can be used strategically to protect survivors
 */
public class SafeZoneTile extends MapTile {

    /**
     * Constructs a safe zone tile at a given position.
     *
     * @param p tile coordinate
     */
    public SafeZoneTile(Point p) {
        super(p);
    }

    /**
     * Entry rules:
     * - Zombies cannot enter
     * - Other entities can enter if tile is empty
     */
    @Override
    public boolean canEnter(Entity e) {
        if (e.getType() == Unit.ZOMBIE) {
            return false;
        }
        return entity == null;
    }

    /**
     * Returns the color used to render safe zones.
     */
    @Override
    public Color getColor() {
        return Color.CYAN;
    }
}
