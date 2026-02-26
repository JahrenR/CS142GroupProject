package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;

import java.awt.*;
import java.util.Random;

public abstract class Entity {
    public Point p;
    public int size;
    Random rand = new Random();

    public Entity() {
    }
    abstract Direction getMove(SimModel model);

    Direction randomDirection() {
        Direction[] dirs = Direction.values();
        int index = rand.nextInt(dirs.length);
        return dirs[index];
    }

    public void setPosition(Point p) {
        this.p = new Point(p);
    }

    public void setBound(int size){this.size = size;}
    public int getX() {return p.x;}
    public int getY() {return p.y;}
    public Point getLocation() {return new Point(p);}


    //die

    //convert


}
