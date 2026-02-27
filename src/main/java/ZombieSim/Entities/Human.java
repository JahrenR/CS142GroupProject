package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;

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
        // 1/3 chance to stay still
        if(rand.nextInt(3)==0) return Direction.STAY;
        steps++;
        if (steps >= 3) {
            direction = randomDirection();
            steps = 0;
        }

        return direction;
    }

    @Override
    public boolean isHuman() {return true;}
    @Override
    public boolean isRecruit() {return true;}

}
