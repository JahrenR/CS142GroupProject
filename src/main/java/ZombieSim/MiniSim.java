package ZombieSim;
import ZombieSim.Entities.*;

import java.awt.*;
import java.util.Scanner;

public class MiniSim {
    static void main(String[] args) {
        SimMap map = new SimMap(5);
        map.spawn(new Human(), new Point(5, 5));
        map.spawn(new Zombie(), new Point(1, 1));
        System.out.println(map);
    }

    private static int getSize(){
        Scanner input = new Scanner(System.in);
        System.out.print("Map Size: ");
        return input.nextInt();
    }
}
