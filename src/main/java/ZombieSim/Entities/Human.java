package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;
import ZombieSim.Unit;
import java.awt.*;

public class Human extends Entity {
    int steps;
    Direction direction;

    public Human(){
        steps = 0;
        direction = randomDirection();
    }

    public String toString() {return "H";}

    @Override
    public Direction getMove(SimModel model){
        // 1/3 chance to stay still - no movement
        if(rand.nextInt(3)==0) return Direction.STAY;
        steps++;
        // change direction after 3 steps
        if (steps >= 3) {
            direction = randomDirection();
            steps = 0;
        }

        return direction;
    }
    @Override
    // humans do not interact with other entities
    public void interact(SimModel model) {}

}
