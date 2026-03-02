package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;
import ZombieSim.Unit;
import java.util.List;

public class General extends Soldier {

    public General() {
        this.type = Unit.GENERAL;
    }

    @Override
    public Direction getMove(SimModel model) {
        // find the closest human to recruit as a soldier
        Entity nearest = nearestRecruit(model);
        // if not human is found, moves normally like a soldier
        if(nearest == null) {return super.getMove(model);}
        return chaseTo(nearest.getLocation(), getLocation());
    }
    @Override
    public void interact(SimModel model) {
        // recruits the human if it's found
        Entity target = model.seekNeighbor(this, Unit.HUMAN);
        if (target != null) {
           model.recruit(target);
        }
    }
        // goes block by block to find the nearest human
    private Entity nearestRecruit(SimModel model) {
        Entity nearest = null;
        int least = Integer.MAX_VALUE;
        List<Entity> entities = model.getEntities();
        for (Entity entity : entities) {
            if (entity.getType() == Unit.HUMAN) {
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
        return "G";
    }
}
