package ZombieSim;

import java.util.ArrayList;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        ArrayList<Integer> list = getParameters();

        SimModel model = new SimModel(
                list.get(0),  // size
                list.get(1),  // civilians
                list.get(2),  // zombies
                list.get(3),  // soldiers
                list.get(4)   //Generals
        );

        new SimGUI(model);
    }

    private static ArrayList<Integer> getParameters() {

        Scanner input = new Scanner(System.in);
        ArrayList<Integer> list = new ArrayList<>();

        System.out.print("Map Size: ");
        list.add(input.nextInt());

        System.out.print("Number of Civilians: ");
        list.add(input.nextInt());

        System.out.print("Number of Zombies: ");
        list.add(input.nextInt());

        System.out.print("Number of Soldiers: ");
        list.add(input.nextInt());

        System.out.print("Number of Generals: ");
        list.add(input.nextInt());

        return list;
    }
}