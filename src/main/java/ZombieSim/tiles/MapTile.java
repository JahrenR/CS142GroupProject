package ZombieSim.tiles;

import ZombieSim.Entity;
import java.awt.*;
import java.awt.Point;

public abstract class MapTile {
    protected final Point p;
    protected Entity entity;

    public MapTile(Point p) {
        this.p = new Point(p);
        this.entity = null;

    }
    public abstract boolean canEnter(Entity e);

    public abstract Color getColor();

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity e) {
        this.entity = e;
    }

    public Point getPoint() {
        return new Point(p);
    }

    public int getX() {
        return p.x;
    }

    public int getY() {
        return p.y;
    }
}

