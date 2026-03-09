package ZombieSim;

import ZombieSim.Entities.*;

import javax.swing.text.Position;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*-------------------------The Zombie Apocalypse Model-------------------------
 *      Start by constructs map with size and spawns entities on the map
 *       Then handles the running rules and logic behind the simulation
 */

public class SimModel {
    List<Entity> entities = new ArrayList<>();
    private final List<Entity> toAdd = new ArrayList<>();
    private final List<Entity> toRemove = new ArrayList<>();

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

    /*----------------------------------Update----------------------------------
     *           It loops through units of map, moving them in turns
     *      could be improved if we check intents and collisions beforehand
     *           currently its just moving each unit randomly
     */

    public void update() {
        toAdd.clear();
        toRemove.clear();
        for (Entity unit : entities) {
            Direction direction = unit.getMove(this);
            map.move(unit, direction);
            unit.interact(this);
        }

        for (Entity unit : toRemove) {
            map.remove(unit);
            entities.remove(unit);
        }

        for (Entity unit : toAdd) {
            map.spawn(unit, unit.getLocation());
            entities.add(unit);
        }
    }

    /*---------------------de/spawn requests------------------------
     *      For handling simultaneous despawn/spawn of units
     */

    public void despawnRequest(Entity entity) {
        if (entity != null) {toRemove.add(entity);}
    }

    public void spawnRequest(Entity entity, Point p) {
        if (entity == null) return;
        toAdd.add(entity);
        entity.setPosition(new Point(p));

    }

    public void despawn(Entity entity) {
        despawnRequest(entity);
    }

    /*--------------------Unit Converting methods--------------------------
    *            zombify converts human to zombie
    *            recruit converts human to soldier
    */

    public void zombify(Entity entity) {
        Point p = entity.getLocation();
        despawnRequest(entity);
        spawnRequest(new Zombie(), p);
    }

    public void recruit(Entity entity) {
        Point p = entity.getLocation();
        despawnRequest(entity);
        spawnRequest(new Soldier(), p);
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

            Zombie z = new Zombie();
            map.spawn(z,p);
            entities.add(z);
        }
    }
    private void spawnZombie(Point p){
        if(map.getUnit(p) != null) return;
        Zombie z = new Zombie();
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

            Human h = new Human();
            map.spawn(h,p);
            entities.add(h);
        }
    }
    private void spawnHuman(Point p){
        if(map.getUnit(p) != null) return;
        Human h = new Human();
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

            Soldier s = new Soldier();
            map.spawn(s,p);
            entities.add(s);
        }
    }
    private void spawnSoldier(Point p){
        if(map.getUnit(p) != null) return;
        Soldier s = new Soldier();
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

            General g = new General();
            map.spawn(g,p);
            entities.add(g);
        }
    }
    private void spawnGeneral(Point p){
        if(map.getUnit(p) != null) return;
        General g = new General();
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

    //--------------------get unit with metadata for gui---------------------

    public Entity getUnit(int c, int r) {
        int x = c + 1;
        int y = map.size() - r;
        return map.getUnit(new Point(x, y));
    }

    //--------------look and returns the neighbor of given unit--------------
    //need revamping
    public Entity seekNeighbor(Entity unit, Unit type) {
        Point p = unit.getLocation();

        //seeks in order of clockwise after north tile
        Entity n = map.getUnit(new Point(p.x, p.y + 1));
        if (n != null && n.getType() == type) return n;

        Entity ne = map.getUnit(new Point(p.x + 1, p.y + 1));
        if (ne != null && ne.getType() == type) return ne;

        Entity e = map.getUnit(new Point(p.x + 1, p.y));
        if (e != null && e.getType() == type) return e;

        Entity se = map.getUnit(new Point(p.x - 1, p.y - 1));
        if (se != null && se.getType() == type) return se;

        Entity s = map.getUnit(new Point(p.x, p.y - 1));
        if (s != null && s.getType() == type) return s;

        Entity sw = map.getUnit(new Point(p.x + 1, p.y - 1));
        if (sw != null && sw.getType() == type) return sw;

        Entity w = map.getUnit(new Point(p.x - 1, p.y));
        if (w != null && w.getType() == type) return w;

        Entity nw = map.getUnit(new Point(p.x - 1, p.y + 1));
        if (nw != null && nw.getType() == type) return nw;

        //returns null if not found
        return null;
    }public Entity seekNeighbor(Entity unit, Unit type, int i) {
        Point p = unit.getLocation();

        //seeks in order of clockwise after north tile
        Entity n = map.getUnit(new Point(p.x, p.y + i));
        if (n != null && n.getType() == type) return n;

        Entity ne = map.getUnit(new Point(p.x + i, p.y + i));
        if (ne != null && ne.getType() == type) return ne;

        Entity e = map.getUnit(new Point(p.x + i, p.y));
        if (e != null && e.getType() == type) return e;

        Entity se = map.getUnit(new Point(p.x - i, p.y - i));
        if (se != null && se.getType() == type) return se;

        Entity s = map.getUnit(new Point(p.x, p.y - 1));
        if (s != null && s.getType() == type) return s;

        Entity sw = map.getUnit(new Point(p.x + i, p.y - i));
        if (sw != null && sw.getType() == type) return sw;

        Entity w = map.getUnit(new Point(p.x - i, p.y));
        if (w != null && w.getType() == type) return w;

        Entity nw = map.getUnit(new Point(p.x - i, p.y + i));
        if (nw != null && nw.getType() == type) return nw;

        //returns null if not found
        return null;
    }

    // - Entities Counters -

    public int countHumans() {
        int count = 0;
        for (Entity e : entities) {
            if (e instanceof Human && !(e instanceof Soldier)) {
                count++;
            }
        }
        return count;
    }

    public int countSoldiers() {
        int count = 0;
        for (Entity e : entities) {
            if (e instanceof Soldier && !(e instanceof General)) {
                count++;
            }
        }
        return count;
    }

    public int countGenerals() {
        int count = 0;
        for (Entity e : entities) {
            if (e instanceof General) {
                count++;
            }
        }
        return count;
    }

    public int countZombies() {
        int count = 0;
        for (Entity e : entities) {
            if (e instanceof Zombie) {
                count++;
            }
        }
        return count;
    }


}
