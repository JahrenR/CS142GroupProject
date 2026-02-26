package ZombieSim;
import ZombieSim.Entities.*;

import java.awt.*;
import static ZombieSim.Direction.*;

public class MiniSim {
    static void main(String[] args) {
        SimModel model = new SimModel(50, 500, 1, 0, 1);
        model.printMap();
    }
}
