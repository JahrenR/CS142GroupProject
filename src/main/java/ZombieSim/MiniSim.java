package ZombieSim;

import ZombieSim.Entities.Human;

import java.util.Scanner;

public class MiniSim {
    static void main(String[] args) {
        SimMap map = new SimMap(getSize());
        map.spawn(new Human(), 3, 2);
        System.out.println(map);
    }

    private static int getSize(){
        Scanner input = new Scanner(System.in);
        System.out.print("Map Size: ");
        return input.nextInt();
    }
}
