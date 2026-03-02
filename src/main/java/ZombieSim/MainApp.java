package ZombieSim;
import java.util.List;

//the Main App of zombie simulation
public class MainApp {
    public static void main(String[] args) {

        //Starts mini GUI to get values for the simulation
        MiniGUI miniGUI = new MiniGUI();
        List<Integer> list = miniGUI.getValues();

        //stops the program if given values is null
        if (list == null) {
            System.exit(0);
        }

        //starts the zombie simulation
        SimModel model = new SimModel(
                list.get(0),
                list.get(1),
                list.get(2),
                list.get(3),
                list.get(4));

        new SimGUI(model);
    }
}