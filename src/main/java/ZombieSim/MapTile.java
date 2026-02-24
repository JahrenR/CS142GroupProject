package ZombieSim;

import ZombieSim.Entities.Entity;

public class MapTile {

    private final int x;
    private final int y;

    Entity tileUnit;

    MapTile(int x, int y) {
        this.x = x;
        this.y = y;
        tileUnit = null;
    }
    public Entity getEntity() {
        return tileUnit;
    }
    public void setEntity(Entity tileUnit) {
        this.tileUnit = tileUnit;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

}
