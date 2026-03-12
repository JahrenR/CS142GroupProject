package ZombieSim.tiles;

import ZombieSim.Entity;
import java.awt.*;

public class WallTile extends MapTile {

    public WallTile(Point p) {
        super(p);
    }

    @Override
    public boolean canEnter(Entity e) {
        return false;
    }

    @Override
    public Color getColor() {
        return Color.DARK_GRAY;
    }
}
