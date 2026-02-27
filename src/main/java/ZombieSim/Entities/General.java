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
        Entity nearest = nearestRecruit(model);
        if(nearest == null) {return super.getMove(model);}
        return chaseTo(nearest.getLocation(), getLocation());
    }

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
