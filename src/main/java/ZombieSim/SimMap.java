package ZombieSim;

import java.awt.*;
import ZombieSim.tiles.MapTile;
import ZombieSim.tiles.GrassTile;

/*-------------------------The Map of Simulation---------------------------
 *          with the given size, it will make square map of tiles
 *         that holds entities in it and allow them to move around
 */

public class SimMap {
    private final int size; // class fields that store map w/h and 2D array - grid storage
    MapTile[][] simMap;

    /*-------------------------MapTile Class---------------------------
     *            Helper Class to handle map tile
     *            Allowing unit to occupy it
     *             If no units, it will be null
     *             Represents one square on grid, can't hold more than 1 e.
     */

    /*
    public static class MapTile {
        private final Point p; // tile coordinate
        Entity tileUnit; // entity currently on the tile

        MapTile(Point p) {
            this.p = new Point(p);
            tileUnit = null;  // copies point so it can't be modified on outside
        }                     // starts empty

        public Entity getEntity() {
            return tileUnit;
        }
        public void setEntity(Entity tileUnit) {
            this.tileUnit = tileUnit; // place/remove entity
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

    private void createMap() { // builds every tile in the 2d array
        simMap = new MapTile[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Point p = new Point(col + 1, size - row);
                simMap[row][col] = new GrassTile(p);
            }
        }
    }

    /*-------------------------Entity Spawn---------------------------
     *           This will spawn the given entity onto tile,
     *                if the point is out of bounds or
     *            tile is already occupied, it will not spawn
     */

    public void spawn(Entity unit, Point p) {
        if(outBounds(p)) {return;} // out of bounds = do nothing
        if(getUnit(p) != null) {return;} // already an entity there = do nothing
        tileAt(p).setEntity(unit);  // sets entity at a tile
        unit.setPosition(new Point(p)); // update entity internal position
        // map and entity need to agree on where the entity is.
    }



    /*-------------------------Move Entity---------------------------
     *          This moves the entity with given direction
     *       It will return if out of bound, or empty/null values
     *          It will return if direction given is STAY
     */

    public void move(Entity unit, Direction direction) {
        if(unit == null || direction == null) return; // ignore invalid inputs
        if(direction == Direction.STAY) return; // if direction = stay then do nothing
        Point from = unit.getLocation();  //read current postion

        Point to = new Point(  // computes the destination
                from.x + direction.dx(),
                from.y + direction.dy()
        );

        if(outBounds(to)) return;
        MapTile target = tileAt(to);
        if (!target.canEnter(unit)) return;
        if(!isEmpty(to)) return;    //if OOB or not empty --> cancel move

        tileAt(from).setEntity(null);    // otherwise clear old tile --> set new tile + update entity pos.
        tileAt(to).setEntity(unit);

        unit.setPosition(new Point(to));
        // prevents leaving the map + prevent 2 entities on same tile
    }

    // -------------------Removes Entity from Map -------------------------

    public void remove(Entity unit) {
        if (unit == null) return;

        Point p = unit.getLocation();
                if(outBounds(p)) return;

                tileAt(p).setEntity(null);
            // clears the tile
    }

    /*-------------------------Helpers---------------------------
     *        Various helpers for across classes
     */

    public boolean outBounds(Point p) {
        if(p == null) {return true;}
        return (p.x < 1 || p.x > size) || (p.y < 1 || p.y > size);
    }

    // checks if a coordinate is outside of the map size

    private MapTile tileAt(Point p) {
        int col = p.x-1;
        int row = size-p.y;
        return simMap[row][col];

        // coordinates to array indices
    }

    public Entity getUnit(Point p) {
        if(outBounds(p)) {return null;}
        return tileAt(p).getEntity();
    }

    public boolean isEmpty(Point p) {
        if(outBounds(p)) return false; // retirn entity at coord
        return tileAt(p).getEntity() == null; // tile exists and has no entity
    }

    //------------------Return map size _______________________//
    public int size(){
        return size;
    }

    public MapTile getTile(Point p) {
        if (outBounds(p)) return null;
        return tileAt(p);
    }

    //--------------------for printing map to console--------------------
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
