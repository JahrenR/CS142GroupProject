package ZombieSim;

import java.awt.*;
import java.util.Random;

public class Entity {
    private Point p;
    private Unit type;
    private int steps = 0;
    private Direction direction;

    /*------------------------------------Entity Constructor------------------------------------
     *          We originally had this being abstract class with instances of units
     *          Then we considered due to conversion going on between entities proved
     *          it to be much more effective to utilize constant types and behaviors
     *          within single class so the model runs much more efficiently changing
     *          entities types for conversions instead of creating and removing many
     *          entities instances.
     */

    public Entity(Unit type) {
        this.type = type;
        direction = randomDirection();
    }

    //----------------------------------The Move Behaviors-------------------------------------

    public Direction getMove(SimModel model) {
        // humans move at half speed in water
        if (type != Unit.ZOMBIE && model.isWater(getLocation())) {
            if (model.getTicks() % 2 == 1) {
                return Direction.STAY;
            }
        }

        //Moves each unit based on their types
        return switch (type) {
            case HUMAN -> baseMove(model);
            case ZOMBIE -> zombieMove(model);
            case SOLDIER -> soldierMove(model);
            case GENERAL -> generalMove(model);
        };
    }

    // base movement for any entity by wandering
    public Direction baseMove(SimModel model){
        // 1/3 chance to stay still - no movement
        if (rand.nextInt(3) == 0) {
            return Direction.STAY;
        }

        steps++;

        // change direction after 3 steps OR if blocked
        if (steps >= 3 || !model.canMove(this, direction)) {
            direction = model.randomOpenDirection(this);
            steps = 0;
        }

        return direction;
    }

    public Direction zombieMove(SimModel model) {
        Entity nearest = model.findNearest(this,
                Unit.HUMAN, Unit.SOLDIER, Unit.GENERAL);

        if (nearest == null) {
            return baseMove(model);
        }

        return chaseTo(nearest.getLocation(), getLocation(), model);
    }

    public Direction soldierMove(SimModel model) {
        Entity nearest = model.findNearest(this, Unit.ZOMBIE);

        if (nearest == null) {
            return baseMove(model);
        }

        return chaseTo(nearest.getLocation(), getLocation(), model);
    }

    public Direction generalMove(SimModel model) {
        Entity nearest = model.findNearest(this, Unit.HUMAN);

        if (nearest == null) {
            return baseMove(model);
        }

        return chaseTo(nearest.getLocation(), getLocation(), model);
    }

    //------------------Interactions for Unit types assigned to Entity--------------------------

    public void interact(SimModel model) {
        switch (type) {
            case HUMAN -> {}
            case ZOMBIE -> zombieInteract(model);
            case SOLDIER -> soldierInteract(model);
            case GENERAL -> generalInteract(model);
        }
    }

    // Bites humans and turns them into zombie

    private void zombieInteract(SimModel model) {

        Entity target = model.seekNeighbor(this, Unit.HUMAN);

        if (target != null) {
            target.setType(Unit.ZOMBIE);
        }
    }

    // Recruits humans to become into soldiers,
    // as well fights zombie if they meet, with 90% chance of winning

    private void generalInteract(SimModel model) {

        Entity human = model.seekNeighbor(this, Unit.HUMAN);

        if (human != null) {
            human.setType(Unit.SOLDIER);
            return;
        }

        Entity zombie = model.seekNeighbor(this, Unit.ZOMBIE);
        if (zombie == null) return;

        if (rand.nextInt(100) < 90) {
            model.removeEntity(zombie);
        } else {
            this.setType(Unit.ZOMBIE);
        }
    }

    // Kills zombies with 70% chance, turns into zombie if fail

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

    public Direction chaseTo(Point to, Point from, SimModel model) {
        int dx = Integer.compare(to.x, from.x);
        int dy = Integer.compare(to.y, from.y);

        Direction first;
        Direction second;

        int xDiff = Math.abs(to.x - from.x);
        int yDiff = Math.abs(to.y - from.y);

        if (xDiff >= yDiff) {
            first = (dx > 0) ? Direction.EAST : Direction.WEST;
            second = (dy > 0) ? Direction.NORTH : Direction.SOUTH;
        } else {
            first = (dy > 0) ? Direction.NORTH : Direction.SOUTH;
            second = (dx > 0) ? Direction.EAST : Direction.WEST;
        }

        if (to.equals(from)) {
            return Direction.STAY;
        }

        if (model.canMove(this, first)) {
            return first;
        }

        if (model.canMove(this, second)) {
            return second;
        }

        return model.randomOpenDirection(this);
    }

    // gives distance between this and other unit
    public int distance(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

}
