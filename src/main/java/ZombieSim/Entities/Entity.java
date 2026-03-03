package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;
import ZombieSim.Unit;

import java.awt.*;
import java.util.List;
import java.util.Random;

public abstract class Entity {
    // location of the entity on the map
    protected Point p;
    // type of entity
    protected Unit type;
    // weather the entity is alive or not
    protected boolean alive = true;
    protected Entity() {
        this.type = Unit.HUMAN;
    };

    // entity is the abstract base for the classes that exist on the grid
    // for movements
    public abstract Direction getMove(SimModel model);
    // for the different interactions
    public abstract void interact(SimModel model);



    //It returns random direction with stay being 1/3 chance, given by Human

    Random rand = new Random();
    Direction randomDirection() {
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

    //--------------------------getters--------------------------------

    public int getX() {return p.x;}
    public int getY() {return p.y;}
    public Point getLocation() {return new Point(p);}
    public Unit getType(){return type;}
    public boolean isAlive() {return alive;}
    public boolean isRecruitable() {return true;}

    //----------------------------Chaser Helpers--------------------------

    public Direction chaseTo(Point to, Point from) {
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
        return randomDirection();
    }

    // the manhattan math that helps determine which closest unit
    public int manhattan(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

}
// the entity class is the base for all the entities in the project
// every entity has stored a point, unit type, and if the entity is alive or not
// every entity uses methods to move and to interacts with other entities
