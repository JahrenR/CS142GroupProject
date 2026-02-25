package ZombieSim;
import ZombieSim.Entities.*;

import java.awt.*;
import static ZombieSim.Direction.*;

public class MiniSim {
    static void main(String[] args) {
        SimMap map = new SimMap(5);

        Human human = new Human();
        Zombie zombie = new Zombie();

        map.spawn(human, new Point(5, 5));
        map.spawn(zombie, new Point(1, 1));

        System.out.println(map);

        System.out.println("Moving Zombie North");
        map.move(zombie, NORTH);
        System.out.println(map);

        System.out.println("Moving Zombie East");
        map.move(zombie, EAST);
        System.out.println(map);

        System.out.println("Moving Zombie South");
        map.move(zombie, SOUTH);
        System.out.println(map);

        System.out.println("Moving Zombie West");
        map.move(zombie, WEST);
        System.out.println(map);
    }
}
