package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;

import java.awt.*;
import java.util.Random;

public abstract class Entity {
    private Point p;

    Random rand = new Random();

    //--------------Abstract for entities instances' getMove------------
    public abstract Direction getMove(SimModel model);

    //------It returns random direction with stay being 1/3 chance------
    Direction randomDirection() {
        // 1/3 chance to stay still
        if(rand.nextInt(3)==0) return Direction.STAY;

        Direction[] direction = {
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST
        };

        return direction[rand.nextInt(direction.length)];
    }
    //------------sets this entity to new position----------------------
    public void setPosition(Point p) {
        this.p = new Point(p);
    }

    //--------------------------getters---------------------------------
    public int getX() {return p.x;}
    public int getY() {return p.y;}
    public Point getLocation() {return new Point(p);}


    //die

    //convert


}
