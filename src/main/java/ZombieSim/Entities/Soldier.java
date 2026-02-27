package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;

import java.awt.*;
import java.util.List;

public class Soldier extends Human {

    @Override
    public Direction getMove(SimModel model) {
        Entity nearest = nearestZombie(model);
        if(nearest == null) {return super.getMove(model);}
        return chaseTo(nearest.getLocation(), getLocation());
    }

    private Entity nearestZombie(SimModel model) {
        Entity nearest = null;
        int least = Integer.MAX_VALUE;
        List<Entity> entities = model.getEntities();
        for (Entity entity : entities) {
            if (entity.isZombie()) {
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
    @Override
    public boolean isRecruitable() {return false;}
}
