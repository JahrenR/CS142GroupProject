package ZombieSim;
import ZombieSim.Entities.*;

import java.awt.*;
import java.util.Scanner;

import static ZombieSim.Direction.*;

public class MiniSim {

    static void main(String[] args) {
        SimModel model = new SimModel(50, 30, 1, 1, 1);
        simulate(model);
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
