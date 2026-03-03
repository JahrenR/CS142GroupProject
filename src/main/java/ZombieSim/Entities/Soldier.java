package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;
import ZombieSim.Unit;

import java.awt.*;
import java.util.List;

public class Soldier extends Human {

    //constructs with a unit type
    public Soldier() {
        this.isRecruitable = false;
    }

    //chases nearest zombie
    @Override
    public Direction getMove(SimModel model) {
        Entity nearest = nearestZombie(model);
        if(nearest == null) {return super.getMove(model);}
        return chaseTo(nearest.getLocation(), getLocation());
    }

    //kills zombies
    @Override
    public void interact(SimModel model) {
        Entity target = model.seekNeighbor(this, Unit.ZOMBIE, 2);
        if (target != null) {
            model.despawn(target);
        }
    }

    //scouts out nearest zombie unit to this unit
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
