package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;
import ZombieSim.Unit;
import java.awt.*;
import java.util.List;

public class Soldier extends Human {
    public Soldier() {
        this.type = Unit.SOLDIER;
    }

    @Override
    public Direction getMove(SimModel model) {
        // finds the closest zombie
        Entity nearest = nearestZombie(model);
        // if no zombie exists move normally
        if(nearest == null) {return super.getMove(model);}
        return chaseTo(nearest.getLocation(), getLocation());
    }
    @Override
    public void interact(SimModel model) {
        // removes an adjacent zombie if found
        Entity target = model.seekNeighbor(this, Unit.ZOMBIE);
        if (target != null) {
            model.despawn(target);
        }
    }
        // goes block by block to find the nearest zombie
    private Entity nearestZombie(SimModel model) {
        Entity nearest = null;
        int least = Integer.MAX_VALUE;
        List<Entity> entities = model.getEntities();
        for (Entity entity : entities) {
            if (entity.getType() == Unit.ZOMBIE) {
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
        return "S";
    }
}
