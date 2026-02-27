package ZombieSim;

import ZombieSim.Entities.*;
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
    }

    /*-------------------------Entities Spawn Methods----------------------------
     *      It spawns amount of entities randomly onto the map of size
     *              Then adds to the list of entities
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
   // ------------------------------End of Spawn Methods-----------------------------------

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



}
