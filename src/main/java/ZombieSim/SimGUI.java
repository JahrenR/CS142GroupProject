package ZombieSim;

import ZombieSim.Entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SimGUI extends JFrame {
    private final static int FRAME_WIDTH = 600;
    private final static int FRAME_HEIGHT = 600;
    private final static int ANIMATION_SPEED = 100;
    private final static int SIZE = 20;
    private final int rows;
    private final int cols;
    private boolean closed = false;

    SimModel model;

    JPanel gridPanel = new JPanel();
    JPanel[][] gridMap;

    Timer timer = new Timer(ANIMATION_SPEED, _ -> update());

    public SimGUI(SimModel model) {
        this.model = model;
        rows = model.getMap().size();
        cols = model.getMap().size();
        setFrame();
        timer.start();
    }

    public void update(){
        model.update();
        paintGrid();
    }

    private void setFrame() {
        this.setTitle("Game of Life");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        closeListener();

        buildGrid();

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void closeListener() {
        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {closed = true;}});
    }

    private void buildGrid() {
        setLayout(new BorderLayout());
        gridPanel = new JPanel(new GridLayout(rows, cols));
        gridMap = new JPanel[rows][cols];

        gridPanel.setPreferredSize(new Dimension(cols*SIZE, rows*SIZE));

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                JPanel p = new JPanel();
                gridMap[r][c] = p;
                gridPanel.add(p);
            }
        }
        paintGrid();
        add(gridPanel, BorderLayout.CENTER);
        pack();
    }



    //-------------------------Paints tiles based on units on the tile-----------------------------------------
    public void paintGrid(){
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
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

    //------------------------When the gui is disposed, it tells MainApp and stops timer----------------------------
    @Override
    public void dispose() {
        timer.stop();
        closed = true;
        super.dispose();
    }

    //--------------------------------helpers for consoleSleep in simulation----------------------------------------
    public boolean isClosed() {return closed;}
    public int getSpeed() {return ANIMATION_SPEED;}
}

