package ZombieSim;

import java.awt.*;
import java.util.Random;

public class Entity {
    private Point p;
    private Unit type;
    private int steps = 0;
    private Direction direction;

    public Entity(Unit type) {
        this.type = type;
        direction = randomDirection();
    };

    public Direction getMove(SimModel model) {
        return switch (type) {
            case HUMAN -> baseMove(model);
            case ZOMBIE -> zombieMove(model);
            case SOLDIER -> soldierMove(model);
            case GENERAL -> generalMove(model);
        };
    }

    public Direction baseMove(SimModel model){
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
    public Direction zombieMove(SimModel model) {
        Entity nearest = model.findNearest(this, Unit.HUMAN, Unit.SOLDIER,  Unit.GENERAL);
        if (nearest == null) {return baseMove(model);}
        return chaseTo(nearest.getLocation(),getLocation());
    }
    public Direction soldierMove(SimModel model) {
        Entity nearest = model.findNearest(this, Unit.ZOMBIE);
        if (nearest == null) {return baseMove(model);}
        return chaseTo(nearest.getLocation(),getLocation());
    }
    public Direction generalMove(SimModel model) {
        Entity nearest = model.findNearest(this, Unit.HUMAN);
        if (nearest == null) {return baseMove(model);}
        return chaseTo(nearest.getLocation(),getLocation());
    }

    // for the different interactions
    public void interact(SimModel model) {
        switch (type) {
            case HUMAN -> {}
            case ZOMBIE -> zombieInteract(model);
            case SOLDIER -> soldierInteract(model);
            case GENERAL -> generalInteract(model);
        }
    };


    private void zombieInteract(SimModel model) {
        Entity target = model.seekNeighbor(this, Unit.HUMAN);

        if (target != null) {
            target.setType(Unit.ZOMBIE);
        }
    }
    private void generalInteract(SimModel model) {
        Entity human = model.seekNeighbor(this, Unit.HUMAN);
        if (human != null) {
            human.setType(Unit.SOLDIER);
            return;
        }

        Entity zombie = model.seekNeighbor(this, Unit.ZOMBIE);
        if (zombie != null) {
            if (rand.nextBoolean()) {
                model.removeEntity(zombie);
            } else {
                this.setType(Unit.ZOMBIE);
            }
        }
    }
    private void soldierInteract(SimModel model) {
        while (true) {
            Entity target = model.seekNeighbor(this, Unit.ZOMBIE);
            if (target == null) return;

            if (rand.nextInt(100) < 70) {
                model.removeEntity(target);
            } else {
                this.setType(Unit.ZOMBIE);
                return;
            }
        }
    }




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
    //------------setters----------------------

    public void setPosition(Point p) {
        this.p = new Point(p);
    }
    public void setType(Unit type) { this.type = type; }

    //--------------------------getters--------------------------------

    public int getX() {return p.x;}
    public int getY() {return p.y;}
    public Point getLocation() {return new Point(p);}
    public Unit getType(){return type;}
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
