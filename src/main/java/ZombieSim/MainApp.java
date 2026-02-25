package ZombieSim;

import java.util.Scanner;

public class MainApp {

    static void main(String[] args) {
        run();
    }
    private static void run(){
        SimMap map = new SimMap(getSize());
        new SimGUI();
    }

    private static int getSize(){
        Scanner input = new Scanner(System.in);
        System.out.println("Map Size: ");
        return input.nextInt();
    }

}
