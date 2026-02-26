package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;

import java.awt.*;

public class Human extends Entity {

    public String toString() {return "H";}

    public Direction getMove(SimModel model){
        return randomDirection();
    }


}
