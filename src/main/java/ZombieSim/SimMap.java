package ZombieSim;

import ZombieSim.Entities.*;

import java.awt.*;

/*-------------------------The Map of Simulation---------------------------
 *          with the given size, it will make square map of tiles
 *         that holds entities in it and allow them to move around
 */

public class SimMap {
    private final int size;
    private MapTile[][] simMap;

    /*-------------------------MapTile Class---------------------------
     *            Mini Class to handle map tile
     *            Allowing unit to occupy it
     *             If no units, it will be null
     */

    public static class MapTile {

        private final Point p;

        Entity tileUnit;

        MapTile(Point p) {
            this.p = new Point(p);
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
        public Point getPoint() {return new Point(p);}

    }

    /*-------------------------Construct---------------------------
     *         creates square 2d array of map Tiles
     */

    public SimMap(int size) {
        this.size = size;
        createMap();
    }

    private void createMap() {
        simMap = new MapTile[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                simMap[row][col] = new MapTile(new Point(col + 1, size - row));
            }
        }
    }

    /*-------------------------Entity Spawn---------------------------
     *           This will spawn the given entity onto tile,
     *                if the point is out of bounds or
     *            tile is already occupied, it will not spawn
     */

    public void spawn(Entity unit, Point p) {
        if(outBounds(p)) {return;}
        if(getUnit(p) != null) {return;}
        tileAt(p).setEntity(unit);
        unit.setPosition(new Point(p));
    }

    /*-------------------------Move Entity---------------------------
     *          This moves the entity with given direction
     *       It will return if out bound, or empty/null values
     *          It will return if direction given is STAY
     */

    public void move(Entity unit, Direction direction) {
        if(unit == null || direction == null) return;
        if(direction == Direction.STAY) return;
        Point from = unit.getLocation();

        Point to = new Point(
                from.x + direction.dx(),
                from.y + direction.dy()
        );

        if(outBounds(to)) return;
        if(!isEmpty(to)) return;

        tileAt(from).setEntity(null);
        tileAt(to).setEntity(unit);

        unit.setPosition(new Point(to));
    }

    /*-------------------------Helpers---------------------------
     *        Various helpers for across classes
     */

    public boolean outBounds(Point p) {
        if(p == null) {return true;}
        return (p.x < 1 || p.x > size) || (p.y < 1 || p.y > size);
    }

    private MapTile tileAt(Point p) {
        int col = p.x-1;
        int row = size-p.y;
        return simMap[row][col];
    }

    public Entity getUnit(Point p) {
        if(outBounds(p)) {return null;}
        return tileAt(p).getEntity();
    }

    public boolean isEmpty(Point p) {
        if(outBounds(p)) return false;
        return tileAt(p).getEntity() == null;
    }

    //returns size of the map
    public int size(){
        return size;
    }

    //for printing console map
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = size; y >=1; y--) {
            for (int x = 1; x <= size; x++) {
                Entity e = getUnit(new Point(x,y));
                if(e != null)
                    sb.append(e);
                else
                    sb.append("-");
            }
            if(y > 1) {sb.append("\n");}
        }
        return sb.toString();
    }

}
