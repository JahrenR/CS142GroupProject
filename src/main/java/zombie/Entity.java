package zombie;

public class Entity {
    private int health;
    public Entity(int health) {
        this.health = health;
    }
    public String toString() {
        return "{" + "health=" + health + '}';
    }
}
