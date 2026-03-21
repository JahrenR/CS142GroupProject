package ZombieSim.tiles;

import ZombieSim.Entity;
import java.awt.*;

public class GrassTile extends MapTile {

    public GrassTile(Point p) {
        super(p);
    }

    @Override
    public boolean canEnter(Entity e) {
        return entity == null;
    }

    @Override
    public Color getColor() {
        return new Color(55, 140, 55);
    }
}
