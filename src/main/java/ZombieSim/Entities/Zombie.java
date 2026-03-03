package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;
import ZombieSim.Unit;

import java.util.List;


public class Zombie extends Human {

    //constructs with unit type and setting alive condition to false for zombie
    public Zombie() {
        this.type = Unit.ZOMBIE;
        this.alive = false;
        this.isRecruitable = false;
    }

    //chases to nearest human
    @Override
    public Direction getMove(SimModel model) {
        Entity nearest = nearestHuman(model);
        if (nearest == null) {return super.getMove(model);}
        return chaseTo(nearest.getLocation(),getLocation());
    }

    //bites human and turns them into zombie
    @Override
    public void interact(SimModel model) {
        Entity victim = model.seekNeighbor(this, Unit.HUMAN);

        if (victim != null) {
            model.zombify(victim);
        }
    }

    //scouts out nearest human unit to this unit
    private Entity nearestHuman(SimModel model) {
        Entity nearest = null;
        int least = Integer.MAX_VALUE;
        List<Entity> entities = model.getEntities();
        for (Entity entity : entities) {
            Unit type = entity.getType();
            if (type == Unit.HUMAN) {
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
