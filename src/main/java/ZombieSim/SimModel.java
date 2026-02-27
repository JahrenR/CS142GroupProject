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
        for (Entity unit : entities) {
            Direction direction = unit.getMove(this);
            map.move(unit, direction);
        }
//        handleInteractions();
    }

//    private void handleInteractions()  {
//        List <Entity> copy =  new ArrayList<>(entities);
//        for (Entity entity : copy) {
//            if (!entity.isZombie()) continue;
//            Point zombieLocation = entity.getLocation();
//
//            //check neighbor entity
//            for (Entity other : new ArrayList<>(entities)) {
//                if (!other.isHuman()) continue;
//
//                if (zombieLocation.equals(other.getLocation())) {
//                    zombify((Human) other);
//
//                }
//            }
//        }
//    }

    private Entity seekNeighbor(Unit type) {
        for (Entity unit : entities) {
            Point point = unit.getLocation();
            Entity type1 = getUnit(point.y , point.x, Direction.NORTH);
            Entity type2 = getUnit(point.y , point.x, Direction.WEST);
            Entity type3 = getUnit(point.y , point.x, Direction.EAST);
            Entity type4 = getUnit(point.y , point.x, Direction.SOUTH);
            if (type == type1.getType()) {return type1;}
            if (type == type2.getType()) {return type2;}
            if (type == type3.getType()) {return type3;}
            if (type == type4.getType()) {return type4;}
        }
        return null;
    }

    public void zombify(Entity entity) {
        Point p = entity.getLocation();
        despawn(entity);
        spawnZombie(p);
    }

    public void despawn(Entity entity) {
        entity.die(this);
    }


    // this ArrayList was added after the addition of the death of an entity in Entity Class

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

    //prints the map to console
    public void printMap(){
        System.out.println(map.toString());
    }

    //getters
    public SimMap getMap() {return map;}
    public List<Entity> getEntities() {return entities;}

    //get unit with metadata for gui
    public Entity getUnit(int c, int r) {
        int x = c + 1;
        int y = map.size() - r;
        return map.getUnit(new Point(x, y));
    }
    public Entity getUnit(int c, int r, Direction direction) {
        int x = c + 1;
        int y = map.size() - r;
        x += direction.dx();
        y += direction.dy();
        return map.getUnit(new Point(x, y));
    }

    public void removeEntity(Entity e){
        entities.remove(e);
        map.remove(e);
    }



}
