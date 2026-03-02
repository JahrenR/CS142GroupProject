package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;
import ZombieSim.Unit;

import java.awt.*;

public class Human extends Entity {
    //for randomly walking
    int steps;
    Direction direction;

    //constructs with steps and randomized N/S/E/W direction
    public Human(){
        steps = 0;
        direction = randomDirection();
    }

    //human moves forward in 3 tiles before changing direction randomly
    @Override
    public Direction getMove(SimModel model){
        // 1/3 chance to stay still
        if(rand.nextInt(3)==0) return Direction.STAY;
        steps++;
        if (steps >= 3) {
            direction = randomDirection();
            steps = 0;
        }

        return direction;
    }

    //human have no interaction action
    @Override
    public void interact(SimModel model) {}

    public String toString() {return "H";}
}
