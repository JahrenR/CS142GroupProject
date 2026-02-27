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

        simulate(model);
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

    private static void simulate(SimModel model) {
        Scanner scanner = new Scanner(System.in);
        loop: do {
            System.out.print("a)nimate, t)ick, q)uit? ");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "a":
                    SimGUI gui = new SimGUI(model);
                    consoleSleep(gui);
                    model.printMap();
                    break;
                case "t":
                    model.update();
                    model.printMap();
                    break;
                case "q":
                    System.out.println("Have a nice Life!");
                    break loop;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while (true);
    }

    private static void consoleSleep(SimGUI gui) {
        while (!gui.isClosed()) {
            try {Thread.sleep(gui.getSpeed());}
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}