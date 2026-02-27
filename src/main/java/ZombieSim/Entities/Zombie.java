package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;
import ZombieSim.Unit;

import java.util.List;


public class Zombie extends Human {
    public Zombie() {
        this.type = Unit.ZOMBIE;
        this.alive = false;
    }
    @Override
    public Direction getMove(SimModel model) {
        Entity nearest = nearestHuman(model);
        if (nearest == null) {return super.getMove(model);}
        return chaseTo(nearest.getLocation(),getLocation());
    }
    private Entity nearestHuman(SimModel model) {
        Entity nearest = null;
        int least = Integer.MAX_VALUE;
        List<Entity> entities = model.getEntities();
        for (Entity entity : entities) {
            if (entity.isAlive()) {
                int distance = manhattan(getLocation(), entity.getLocation());
                if (distance < least) {
                    least = distance;
                    nearest = entity;
                }
            }
        }
        return nearest;
    }

    public String toString() {
        return "Z";
    }


}
