package ZombieSim;

import javax.swing.*;

public class SimGUI extends JFrame {
    private final static int SCREEN_WIDTH = 800;
    private final static int SCREEN_HEIGHT = 600;
    SimGUI() {
        this.setTitle("Zombie");
        this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
