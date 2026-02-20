package zombie;

import javax.swing.*;

public class SimFrame extends JFrame {
    SimFrame() {
        this.add(new SimPanel());
        this.setTitle("Zombie");

    }
}
