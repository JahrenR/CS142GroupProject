package ZombieSim.Entities;

import java.awt.*;

public class Entity {
    public Point p;
    public int size;
    public Entity() {
    }

    public void setPosition(Point p) {
        this.p = new Point(p);
    }

    public void setBound(int size){this.size = size;}
    public int getX() {return p.x;}
    public int getY() {return p.y;}
    public Point getLocation() {return new Point(p);}

    //Add Movement with bounds
}
