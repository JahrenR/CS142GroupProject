package ZombieSim;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {

        /*-------------------------Input Phase---------------------------
         *  Launch MiniGUI to retrieve simulation configuration
         *  - mapFile: name of map file (if provided)
         *  - list: fallback values for random map generation
         */

        MiniGUI miniGUI = new MiniGUI();
        String mapFile = miniGUI.getMapFile();
        List<Integer> list = miniGUI.getValues();

        SimModel model;

        /*-------------------------Model Initialization---------------------------
         *  If a map file is provided:
         *      - load simulation from file
         *
         *  Otherwise:
         *      - use user-provided values to generate simulation
         *
         *  If no valid input is given:
         *      - terminate program
         */

        if (mapFile != null) {
            model = new SimModel(mapFile); // file-based simulation

        } else {

            if (list == null) {
                System.exit(0); // exit if user cancels input
            }

            model = new SimModel(
                    list.get(0), // humans
                    list.get(1), // zombies
                    list.get(2), // soldiers
                    list.get(3), // generals
                    list.get(4)  // map size
            );
        }

        /*-------------------------Launch Simulation---------------------------
         *  Create and display the main simulation GUI
         */

        new SimGUI(model);
    }
}