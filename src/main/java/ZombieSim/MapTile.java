package ZombieSim;

import ZombieSim.Entities.*;

import java.awt.*;

public class MapTile {

    private final Point p;

    Entity tileUnit;

    MapTile(Point p) {
        this.p = p;
        tileUnit = null;
    }
    public Entity getEntity() {
        return tileUnit;
    }
    public void setEntity(Entity tileUnit) {
        this.tileUnit = tileUnit;
    }
    public int getX() {return p.x;}
    public int getY() {return p.y;}
    public Point getPoint() {return p;}

}
