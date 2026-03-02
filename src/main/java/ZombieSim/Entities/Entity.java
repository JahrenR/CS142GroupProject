package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;
import ZombieSim.Unit;

import java.awt.*;
import java.util.List;
import java.util.Random;

public abstract class Entity {
    // entity's location on the map
    private Point p;
    // what kind of unit it is between the subclasses
    protected Unit type;
    // weather is alive
    protected boolean alive = true;
    // this is protected so subclasses that have the possibility of changing this can read it and change it
    // because other subclasses, for example soldiers, can change the status of a zombie
    protected Entity() {
        this.type = Unit.HUMAN;
    };

    // Abstract for entities instances for everything that exists on the grid
    public abstract Direction getMove(SimModel model);
    // passes the map and decide where to move at that tick
    public abstract void interact(SimModel model);
    // after moving, do the interaction the subclass is supposed to do

    //------It returns random direction with stay being 1/3 chance------
    Random rand = new Random();
    Direction randomDirection() {
        Direction[] direction = {
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST
        };

        return direction[rand.nextInt(direction.length)];
        // moves randomly in a direction
    }
    //------------sets this entity to new position----------------------
    public void setPosition(Point p) {
        this.p = new Point(p);
    }

    //--------------------------getters---------------------------------
    public int getX() {return p.x;}
    public int getY() {return p.y;}
    public Point getLocation() {return new Point(p);}
    public Unit getType(){return type;}
    public boolean isAlive() {return alive;}
    //----------------------------Chaser Helpers--------------------------

    public static Direction chaseTo(Point to, Point from) {
        int dx = Integer.compare(to.x, from.x);
        int dy = Integer.compare(to.y, from.y);

        int xDiff = Math.abs(to.x - from.x);
        int yDiff = Math.abs(to.y - from.y);

        if (xDiff > yDiff) {
            if (dx == 1) {
                return Direction.EAST;
            } else if (dx == -1) {
                return Direction.WEST;
            }
        } else if (yDiff > xDiff) {
            if (dy == 1) {
                return Direction.NORTH;
            } else {
                return Direction.SOUTH;
            }
        }
        return Direction.STAY;
        // used to move
    }
    // used to give the closest point, and chase
    public int manhattan(Point a, Point b){
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

}
// this entity class is the abstract parent of all the units in the project
// every entity has its own point, unit type and if they are alive or not

// every entity uses methods to move and interact with other classes if possible

