package zombie;

import javax.swing.*;

public class SimFrame extends JFrame {
    SimFrame() {
        this.add(new SimPanel());
        this.setTitle("Zombie");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
