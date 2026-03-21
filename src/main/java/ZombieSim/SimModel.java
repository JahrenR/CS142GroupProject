package ZombieSim;

import ZombieSim.tiles.MapTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ZombieSim.tiles.WaterTile;

/*-------------------------The Zombie Apocalypse Model-------------------------
 *      Start by constructs map with size and spawns entities on the map
 *       Then handles the running rules and logic behind the simulation
 */

public class SimModel {
    List<Entity> entities = new ArrayList<>();
    private int ticks = 0;
    SimMap map;

    /*-------------------------Construct---------------------------
     *           if input given more entities than available spots
     *             on the map, it will throw IllegalArgument
     */

    public SimModel(int size, int humans, int zombies, int soldiers, int generals) {
        this.map = new SimMap(size);
        if (humans + zombies + soldiers + generals < size*size) {
            if(humans  > 0) {spawnHuman(humans);}
            if(zombies > 0) {spawnZombie(zombies);}
            if(soldiers > 0) {spawnSoldier(soldiers);}
            if(generals > 0) {spawnGeneral(generals);}
        } else {
            throw new IllegalArgumentException("Not Enough Space");
        }
    }

    public SimModel(String filename) {

        entities = new ArrayList<>();

        MapTile[][] loadedMap = MapLoader.loadMap(filename, entities);

        int size = loadedMap.length;

        map = new SimMap(size);

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {

                MapTile tile = loadedMap[r][c];
                map.simMap[r][c] = tile;

            }
        }
    }

    /*----------------------------------Update----------------------------------
     *           It loops through units of map, moving them in turns
     *        then check each units' interactions as well environment effects
     */

    public void update() {
        ticks++;

        // move phase
        for (Entity unit : new ArrayList<>(entities)) {
            if (!entities.contains(unit)) {
                continue;
            }

            Direction direction = unit.getMove(this);
            map.move(unit, direction);
        }

        // zombies drown after movement
        applyWaterEffects();

        // interaction phase
        for (Entity unit : new ArrayList<>(entities)) {
            if (!entities.contains(unit)) {
                continue;
            }
            unit.interact(this);
        }
    }

    public void removeEntity(Entity unit) {
        map.remove(unit);       // remove from map
        entities.remove(unit);  // remove from entity list
    }

    /*-------------------------Entities Spawn Methods----------------------------
     *      With amount it will spawns amount of entities randomly
     *          onto the map of size. But if given Point,
     *                it will spawn there once.
     *              Then adds to the list of entities.
     */

    Random rand = new Random();

    private void spawnZombie(int amount){
        Point p = new Point();
        //spawns zombies randomly in the map
        for(int i = 0; i < amount; i++){
            do {//repeat if tile is occupied
                int x = rand.nextInt(map.size()) + 1;
                int y = rand.nextInt(map.size()) + 1;
                p.setLocation(x, y);
            } while (map.getUnit(p) != null);

            Entity z = new Entity(Unit.ZOMBIE);
            map.spawn(z,p);
            entities.add(z);
        }
    }
    private void spawnZombie(Point p){
        if(map.getUnit(p) != null) return;
        Entity z = new Entity(Unit.ZOMBIE);
        map.spawn(z,p);
        entities.add(z);
    }

    private void spawnHuman(int amount){
        Point p = new Point();
        for(int i = 0; i < amount; i++){
            do {
                int x = rand.nextInt(map.size()) + 1;
                int y = rand.nextInt(map.size()) + 1;
                p.setLocation(x, y);
            } while (map.getUnit(p) != null);

            Entity h = new Entity(Unit.HUMAN);
            map.spawn(h,p);
            entities.add(h);
        }
    }
    private void spawnHuman(Point p){
        if(map.getUnit(p) != null) return;
        Entity h = new Entity(Unit.HUMAN);
        map.spawn(h,p);
        entities.add(h);
    }

    private void spawnSoldier(int amount){
        Point p = new Point();
        for(int i = 0; i < amount; i++){
            do {
                int x = rand.nextInt(map.size()) + 1;
                int y = rand.nextInt(map.size()) + 1;
                p.setLocation(x, y);
            }  while (map.getUnit(p) != null);

            Entity s = new Entity(Unit.SOLDIER);
            map.spawn(s,p);
            entities.add(s);
        }
    }
    private void spawnSoldier(Point p){
        if(map.getUnit(p) != null) return;
        Entity s = new Entity(Unit.SOLDIER);
        map.spawn(s,p);
        entities.add(s);
    }

    private void spawnGeneral(int amount){
        Point p = new Point();
        for(int i = 0; i < amount; i++){
            do {
                int x = rand.nextInt(map.size()) + 1;
                int y = rand.nextInt(map.size()) + 1;
                p.setLocation(x, y);
            }  while (map.getUnit(p) != null);

            Entity g = new Entity(Unit.GENERAL);
            map.spawn(g,p);
            entities.add(g);
        }
    }
    private void spawnGeneral(Point p){
        if(map.getUnit(p) != null) return;
        Entity g = new Entity(Unit.GENERAL);
        map.spawn(g,p);
        entities.add(g);
    }

    //-----------------------prints the map to console-----------------------

    public void printMap(){
        System.out.println(map.toString());
    }

    //--------------------------------getters--------------------------------

    public SimMap getMap() {return map;}
    public List<Entity> getEntities() {return entities;}
    public int getTicks() {return ticks;}
    //--------------------get unit and tile with metadata for gui---------------------

    public Entity getUnit(int c, int r) {
        int x = c + 1;
        int y = map.size() - r;
        return map.getUnit(new Point(x, y));
    }

    public MapTile getTile(int c, int r) {
        int x = c + 1;
        int y = map.size() - r;
        return map.getTile(new Point(x, y));
    }

    //--------------------Find Nearest entity helper---------------------
    public Entity findNearest(Entity current, Unit... targets) {
        Entity nearest = null;
        int best = Integer.MAX_VALUE;

        for (Entity e : entities) {
            if (e == current) continue;

            for (Unit target : targets) {
                if (e.getType() == target) {
                    int dist = current.distance(current.getLocation(), e.getLocation());
                    if (dist < best) {
                        best = dist;
                        nearest = e;
                    }
                    break;
                }
            }
        }

        return nearest;
    }

    //--------------look and returns the neighbor of given unit--------------
    public Entity seekNeighbor(Entity unit, Unit... types) {
        Point p = unit.getLocation();

        int[][] offsets = {
                {0, 1}, {1, 1}, {1, 0}, {1, -1},
                {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}
        };

        for (int[] offset : offsets) {
            Entity neighbor = map.getUnit(new Point(p.x + offset[0], p.y + offset[1]));
            if (neighbor != null) {
                for (Unit type : types) {
                    if (neighbor.getType() == type) {
                        return neighbor;
                    }
                }
            }
        }

        return null;
    }

    // - Entities Counters -

    public int countHumans() {
        int count = 0;
        for (Entity e : entities) {
            if (e.getType() == Unit.HUMAN)
                count++;
        }
        return count;
    }


    public int countSoldiers() {
        int count = 0;
        for (Entity e : entities) {
            if (e.getType() == Unit.SOLDIER) {
                count++;
            }
        }
        return count;
    }

    public int countGenerals() {
        int count = 0;
        for (Entity e : entities) {
            if (e.getType() == Unit.GENERAL) {
                count++;
            }
        }
        return count;
    }

    public int countZombies() {
        int count = 0;
        for (Entity e : entities) {
            if (e.getType() == Unit.ZOMBIE) {
                count++;
            }
        }
        return count;
    }


    //---------------------Game End Condition Helpers---------------------

    public boolean isGameOver() {
        return zombiesEliminated() || allSurvivorsInfected();
    }

    public boolean zombiesEliminated() {
        return countZombies() == 0;
    }

    public boolean allSurvivorsInfected() {
        return countHumans() == 0
                && countSoldiers() == 0
                && countGenerals() == 0
                && countZombies() > 0;
    }

    public String getEndMessage() {
        if (zombiesEliminated()) {
            return "Humans won! All zombies were eliminated.";
        }
        if (allSurvivorsInfected()) {
            return "Zombies won! All humans became zombies.";
        }
        return "";
    }

    //-----------------------------Movement checkers and helpers----------------------------

    //  Checker for when unit is in water tile
    public boolean isWater(Point p) {
        return map.getTile(p) instanceof WaterTile;
    }

    //  Drowns zombie when they enter water
    private void applyWaterEffects() {
        for (Entity unit : new ArrayList<>(entities)) {
            if (unit.getType() == Unit.ZOMBIE && isWater(unit.getLocation())) {
                removeEntity(unit);
            }
        }
    }

    // checks whether a unit can move one step in a direction
    public boolean canMove(Entity unit, Direction direction) {
        if (unit == null || direction == null || direction == Direction.STAY) {
            return false;
        }

        Point from = unit.getLocation();
        Point to = new Point(
                from.x + direction.dx(),
                from.y + direction.dy()
        );

        if (map.outBounds(to)) {
            return false;
        }

        MapTile tile = map.getTile(to);
        return tile != null && tile.canEnter(unit) && map.isEmpty(to);
    }

    // returns a random valid direction, or STAY if none work
    public Direction randomOpenDirection(Entity unit) {
        Direction[] dirs = {
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST
        };

        for (int i = 0; i < 10; i++) {
            Direction d = dirs[rand.nextInt(dirs.length)];
            if (canMove(unit, d)) {
                return d;
            }
        }

        return Direction.STAY;
    }

}
