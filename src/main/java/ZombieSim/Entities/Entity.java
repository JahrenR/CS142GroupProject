package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;

import java.awt.*;
import java.util.Random;

public abstract class Entity {
    private Point p;

    Random rand = new Random();

    public abstract Direction getMove(SimModel model);

    Direction randomDirection() {
        Direction[] direction = {
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST
        };
        return direction[rand.nextInt(direction.length)];
    }

    public void setPosition(Point p) {
        this.p = new Point(p);
    }

    public int getX() {return p.x;}
    public int getY() {return p.y;}
    public Point getLocation() {return new Point(p);}


    //die

    //convert


}
