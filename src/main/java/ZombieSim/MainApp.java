package ZombieSim;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
}