package ZombieSim;

import ZombieSim.Entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*------------------Main GUI class--------------------
 *      This GUI initiates with the given model
 *     and constructs map of panels that will be
 *      painted on what unit type is occupying
 */

public class SimGUI extends JFrame {
    private final static int ANIMATION_SPEED = 100;
    private final static int CELL_SIZE = 20;
    private final int size;

    SimModel model;

    JPanel gridPanel = new JPanel();
    JPanel[][] gridMap;

    //timer that updates the simulation
    Timer timer = new Timer(ANIMATION_SPEED, _ -> update());

    //---------------------Construct of SimGUI------------------------
    public SimGUI(SimModel model) {
        this.model = model;
        size = model.getMap().size();
        setFrame();
        timer.start();
    }

    //updates the map by updating model then paints grid
    public void update(){
        model.update();
        paintGrid();
    }

    //---------------------------JFrame set up---------------------------
    private void setFrame() {
        this.setTitle("Zombie Apocalypse");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //creates map grid
        buildGrid();

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    //------------builds grid of panel based on the size of model------------
    private void buildGrid() {
        setLayout(new BorderLayout());
        gridPanel = new JPanel(new GridLayout(size, size));
        gridMap = new JPanel[size][size];

        gridPanel.setPreferredSize(new Dimension(size* CELL_SIZE, size* CELL_SIZE));

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                JPanel p = new JPanel();
                gridMap[r][c] = p;
                gridPanel.add(p);
            }
        }
        paintGrid();
        add(gridPanel, BorderLayout.CENTER);
        pack();
    }



    //---------------Paints tiles based on units on the tile----------------------
    public void paintGrid(){
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                Entity unit = model.getUnit(c,r);
                switch (unit) {
                    case General _ -> gridMap[r][c].setBackground(Color.RED);
                    case Soldier _ -> gridMap[r][c].setBackground(Color.BLACK);
                    case Zombie _ -> gridMap[r][c].setBackground(new Color(111, 161, 25, 255));
                    case Human _ -> gridMap[r][c].setBackground(new Color(195, 149, 130));
                    case null, default -> gridMap[r][c].setBackground(Color.WHITE);
                }
            }
        }
    }

}

