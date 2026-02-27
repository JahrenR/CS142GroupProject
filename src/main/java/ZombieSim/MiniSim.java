package ZombieSim;
import ZombieSim.Entities.*;

import java.awt.*;
import java.util.Scanner;

import static ZombieSim.Direction.*;

public class MiniSim {

    static void main(String[] args) {
        SimModel model = new SimModel(50, 30, 1, 1, 1);
        new SimGUI(model);
    }


}
