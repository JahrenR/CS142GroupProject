package ZombieSim;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import javafx.scene.layout.BorderPane;

public class MainApp {

    public static void main(String[] args) {

        MiniGUI miniGUI = new MiniGUI();
        List<Integer> list = miniGUI.getValues();

        if (list == null) {
            System.exit(0);
        }

        SimModel model = new SimModel(
                list.get(0),
                list.get(1),
                list.get(2),
                list.get(3),
                list.get(4));

        new SimGUI(model);
    }

    private static List<Integer> getParameters() {

        Scanner input = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();

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