package ZombieSim;
import ZombieSim.Entities.*;

import java.util.Scanner;

public class MiniSim {
    static void main(String[] args) {
        SimMap map = new SimMap(getSize());
        map.spawn(new Human(), 3, 2);
        map.spawn(new Zombie(), 4, 2);
        System.out.println(map);
    }

    private static int getSize(){
        Scanner input = new Scanner(System.in);
        System.out.print("Map Size: ");
        return input.nextInt();
    }
}
