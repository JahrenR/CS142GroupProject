package ZombieSim.tiles;

import ZombieSim.Entity;
import ZombieSim.Unit;

import java.awt.*;

public class WaterTile extends MapTile {

    public WaterTile(Point p) {
        super(p);
    }

    public boolean canEnter(Entity e) {
        return e.getType() == Unit.ZOMBIE;
    }

    public Color getColor() {
        return new Color(70,130,180);
    }
}
