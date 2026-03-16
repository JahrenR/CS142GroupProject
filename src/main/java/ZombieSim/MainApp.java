package ZombieSim;
import java.util.List;

//the Main App of zombie simulation
public class MainApp {
    public static void main(String[] args) {

        //Starts mini GUI to get values for the simulation
        MiniGUI miniGUI = new MiniGUI();
        String mapFile = miniGUI.getMapFile();
        List<Integer> list = miniGUI.getValues();

        SimModel model;

        //stops the program if given values is null
        if (mapFile != null) {

            model = new SimModel(mapFile); // file-based constructor

        } else {

            if (list == null) {
                System.exit(0);
            }

            model = new SimModel(
                    list.get(0),
                    list.get(1),
                    list.get(2),
                    list.get(3),
                    list.get(4)
            );
        }

        new TempGUI(model);
    }
}