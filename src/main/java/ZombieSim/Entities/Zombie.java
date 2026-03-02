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
        // finds the nearest human
        Entity nearest = nearestHuman(model);
        // if no target exists, move normally
        if (nearest == null) {return super.getMove(model);}
        return chaseTo(nearest.getLocation(),getLocation());
    }
    @Override
    public void interact(SimModel model) {
        // infects the human if found
        Entity victim = model.seekNeighbor(this, Unit.HUMAN);
        if (victim != null) {
            model.zombify(victim);
        }
    }
    // searched for the closest human, soldier or general
    private Entity nearestHuman(SimModel model) {
        Entity nearest = null;
        int least = Integer.MAX_VALUE;
        List<Entity> entities = model.getEntities();
        for (Entity entity : entities) {
            Unit type = entity.getType();
            if (type == Unit.HUMAN || type == Unit.SOLDIER || type == Unit.GENERAL) {
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
