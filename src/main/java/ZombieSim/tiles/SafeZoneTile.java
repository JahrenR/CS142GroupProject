package ZombieSim.tiles;

import ZombieSim.Entity;
import ZombieSim.Unit;
import java.awt.*;

public class SafeZoneTile extends MapTile {

    public SafeZoneTile(Point p) {
        super(p);
    }

    @Override
    public boolean canEnter(Entity e) {
        if (e.getType() == Unit.ZOMBIE) {
            return false;
        }
        return entity == null;
    }

    @Override
    public Color getColor() {
        return Color.CYAN;
    }
}
