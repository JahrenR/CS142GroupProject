package ZombieSim.tiles;

import ZombieSim.Entity;
import java.awt.*;

/*-------------------------Deep Water Tile---------------------------
 *      This tile extends WaterTile but blocks all movement
 *    No human, zombie, soldier, or general can enter it
 */

public class DeepWaterTile extends WaterTile {

    public DeepWaterTile(Point p) {
        super(p);
    }

    @Override
    public boolean canEnter(Entity e) {
        return false;
    }

    @Override
    public Color getColor() {
        return new Color(25, 70, 140);
    }
}