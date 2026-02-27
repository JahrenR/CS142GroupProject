package ZombieSim.Entities;

import ZombieSim.Direction;
import ZombieSim.SimModel;

import java.awt.*;
import java.util.List;
import java.util.Random;

public abstract class Entity {
    private Point p;

    Random rand = new Random();

    //--------------Abstract for entities instances' getMove------------
    public abstract Direction getMove(SimModel model);

    //------It returns random direction with stay being 1/3 chance------
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

    //--------------------------getters---------------------------------
    public int getX() {return p.x;}
    public int getY() {return p.y;}
    public Point getLocation() {return new Point(p);}

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
    }
    public Entity nearestEntity(SimModel model,boolean condition) {
        Entity nearest = null;
        int least = Integer.MAX_VALUE;
        List<Entity> entities = model.getEntities();
        for (Entity entity : entities) {
            if (condition) {
                int distance = manhattan(getLocation(), entity.getLocation());
                if (distance < least) {
                    least = distance;
                    nearest = entity;
                }
            }
        }
        return nearest;
    }

    public int manhattan(Point a, Point b){
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
    //die

    //convert

    //-------------Flags--------------------
    public boolean isHuman() {return false;}
    public boolean isZombie() {return false;}
    public boolean isRecruitable() {return false;}


}
