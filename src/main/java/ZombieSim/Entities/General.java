package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;
import ZombieSim.Unit;

import java.util.List;

public class General extends Soldier {

    //construct with the unit type
    public General() {
        this.isRecruitable = false;
    }

    //chases nearest human
    @Override
    public Direction getMove(SimModel model) {
        Entity nearest = nearestRecruit(model);
        if(nearest == null) {return super.getMove(model);}
        return chaseTo(nearest.getLocation(), getLocation());
    }

    //turns neighboring human into soldier
    @Override
    public void interact(SimModel model) {
        Entity target = model.seekNeighbor(this, Unit.HUMAN);
        if (target != null) {
           model.recruit(target);
        }
    }

    //scouts out which human unit is nearest to this unit
    private Entity nearestRecruit(SimModel model) {
        Entity nearest = null;
        int least = Integer.MAX_VALUE;
        List<Entity> entities = model.getEntities();
        for (Entity entity : entities) {
            if (entity.isRecruitable()) {
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
