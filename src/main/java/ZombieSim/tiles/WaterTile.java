package ZombieSim.tiles;

import ZombieSim.Entity;

import java.awt.*;

public class WaterTile extends MapTile {

    public WaterTile(Point p) {
        super(p);
    }

    @Override
    public boolean canEnter(Entity e) {
        return true;
    }

    @Override
    public Color getColor() {
        return new Color(70,130,180);
    }
}
