package ZombieSim;

import ZombieSim.Entities.*;

import javax.swing.*;
import java.awt.*;

/*
 * This class is responsible for:
 *   - Creating the main simulation window
 *   - Building the visual grid
 *   - Animating the simulation
 *   - Painting each tile based on what entity occupies it
 *
 *
 * This class does not contain simulation logic.
 * It only displays the state of the SimModel.
 *
 * This follows separation of:
 *   - SimModel = logic
 *   - SimMap = storage
 *   - SimGUI = visualization
 */
public class TempGUI extends JFrame {

    /*
     * ANIMATION_SPEED controls how fast the simulation updates.
     * 100 milliseconds means the simulation updates 10 times per second.
     */
    private final static int ANIMATION_SPEED = 100;

    /*
     * CELL_SIZE controls how big each square appears visually.
     */
    private final static int CELL_SIZE = 20;

    /*
     * The size of the grid (width and height).
     * Retrieved from the model.
     */
    private final int size;

    JLabel humanLabel = new JLabel();
    JLabel zombieLabel = new JLabel();
    JLabel soldierLabel = new JLabel();
    JLabel generalLabel = new JLabel();

    /*
     * Reference to the simulation model.
     * The GUI reads data from this.
     */
    SimModel model;

    /*
     * gridPanel holds all tile panels.
     * gridMap is a 2D array of JPanels representing each tile.
     */
    JPanel gridPanel = new JPanel();
    JPanel[][] gridMap;

    /*
     * Swing Timer:
     * Calls update() every ANIMATION_SPEED milliseconds.
     * This creates the animation effect.
     */
    Timer timer = new Timer(ANIMATION_SPEED, _ -> update());

    /*
     * Constructor
     *
     * Takes a SimModel as input.
     * Builds the frame and starts animation.
     */
    public TempGUI(SimModel model) {
        this.model = model;
        size = model.getMap().size();

        setFrame();   // Build the window
        timer.start(); // Start animation loop
    }

    /*
     * Update Method
     * This runs every animation tick.
     *
     * Steps:
     *   1. Update simulation logic in model
     *   2. Repaint the grid visually
     *
     * This keeps logic and graphics separate.
     */
    public void update(){
        model.update();
        paintGrid();
        updateStats();
    }

    /*
     * ========================= Frame Setup =========================
     *
     * Configures the JFrame properties.
     */
    private void setFrame() {

        this.setTitle("Zombie Apocalypse");

        // Close entire program when window closes
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // creates a window for the stats on the right
        add(buildStatsPanel(), BorderLayout.EAST);

        // Builds the grid layout
        buildGrid();

        this.setResizable(false);
        this.setLocationRelativeTo(null); // Center window
        this.setVisible(true);
    }

    /*
     * Build Grid
     * Creates a size x size grid of JPanels.
     *
     * Each JPanel represents one tile on the map.
     */
    private void buildGrid() {

        gridPanel = new JPanel(new GridLayout(size, size));

        gridMap = new JPanel[size][size];

        /*
         * Set total window size based on:
         * grid size × cell size
         */
        gridPanel.setPreferredSize(
                new Dimension(size * CELL_SIZE, size * CELL_SIZE)
        );

        /*
         * Create each tile panel and store it
         * in the 2D gridMap array.
         */
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {

                JPanel p = new JPanel();

                gridMap[r][c] = p;
                gridPanel.add(p);
            }
        }

        // Paint initial state
        paintGrid();

        add(gridPanel, BorderLayout.CENTER);
        pack();
    }

    private void updateStats() {
        int humans = 0, zombies = 0, soldiers = 0, generals = 0;

        for (int r = 0; r < model.getMap().size(); r++) {
            for (int c = 0; c < model.getMap().size(); c++) {
                Entity e = model.getUnit(c, r);
                if (e instanceof Human) humans++;
                else if (e instanceof Zombie) zombies++;
                else if (e instanceof General) generals++;
                else if (e instanceof Soldier) soldiers++;
            }
        }

        humanLabel.setText("Humans: " + humans);
        zombieLabel.setText("Zombies: " + zombies);
        soldierLabel.setText("Soldiers: " + soldiers);
        generalLabel.setText("Generals: " + generals);
    }

    private JPanel buildStatsPanel() {

        JPanel stats = new JPanel();
        stats.setLayout(new GridLayout(4,1));

        stats.add(humanLabel);
        stats.add(zombieLabel);
        stats.add(soldierLabel);
        stats.add(generalLabel);

        updateStats();

        return stats;
    }

    /*
     * Paint Grid
     *
     * This method updates tile colors based on
     * which entity is occupying each position.
     *
     * It reads from the model.
     *
     * This demonstrates polymorphism:
     * We don’t need to know how entities behave,
     * only what type they are.
     */
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

