package zombie;

import javax.swing.*;
import java.awt.*;

public class SimPanel extends JPanel {
    public final int SCREEN_WIDTH = 800;
    public final int SCREEN_HEIGHT = 600;
    SimPanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
    }

}
