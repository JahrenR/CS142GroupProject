package ZombieSim;

import ZombieSim.tiles.MapTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

/*-------------------------The Simulation GUI---------------------------
 *      This class builds the window for the zombie simulation
 *    It displays the map grid, unit sprites, statistics panel,
 *         and updates the screen every animation tick
 */

public class SimGUI extends JFrame {

    private static final int ANIMATION_SPEED = 100; // milliseconds between updates
    private static final int CELL_SIZE = 24; // width/height of each tile in pixels

    private final int size; // size of the square map
    private final SimModel model; // simulation model being displayed
    private boolean gameEnded = false; // stops updates once game is over

    // labels for live unit counts
    private JLabel humanLabel = new JLabel();
    private JLabel zombieLabel = new JLabel();
    private JLabel soldierLabel = new JLabel();
    private JLabel generalLabel = new JLabel();

    JPanel gridPanel = new JPanel(); // main panel holding the map grid
    JPanel[][] gridMap; // stores each tile panel
    JLabel[][] entityIcons; // stores sprite labels inside each tile

    // maps each Unit type to its sprite icon
    private final Map<Unit, ImageIcon> spriteMap = new EnumMap<>(Unit.class);

    // timer repeatedly calls update() to animate the simulation
    Timer timer = new Timer(ANIMATION_SPEED, _ -> update());

    /*-----------------------Construct---------------------------
     *      Takes the model, loads sprites, builds the frame,
     *               and starts the animation timer
     */

    public SimGUI(SimModel model) {
        this.model = model;
        this.size = model.getMap().size();

        loadSprites();
        setFrame();
        timer.start();
    }

    /*-------------------------Update--------------------------
     *      Runs one GUI update cycle for the simulation
     *       updates model, redraws grid, updates stats,
     *         and checks for game over conditions
     */

    public void update() {
        if (gameEnded) {
            return;
        }

        model.update();
        paintGrid();
        updateStats();

        if (model.isGameOver()) {
            gameEnded = true;
            timer.stop();
            showGameOverPopup();
        }
    }

    /*-------------------------Frame Setup---------------------------
     *         Builds the main JFrame layout and properties
     */

    private void setFrame() {
        setTitle("Zombie Apocalypse");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(buildStatsPanel(), BorderLayout.EAST);
        buildGrid();

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*-----------------------Build Grid---------------------------
     *       Creates the square map of panels and icon labels
     *         then paints the initial grid onto the window
     */

    private void buildGrid() {
        gridPanel = new JPanel(new GridLayout(size, size));
        gridMap = new JPanel[size][size];
        entityIcons = new JLabel[size][size];

        gridPanel.setPreferredSize(new Dimension(size * CELL_SIZE, size * CELL_SIZE));

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                JPanel cell = new JPanel(new BorderLayout());
                cell.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));

                JLabel iconLabel = new JLabel();
                iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
                iconLabel.setVerticalAlignment(SwingConstants.CENTER);

                cell.add(iconLabel, BorderLayout.CENTER);

                gridMap[r][c] = cell;
                entityIcons[r][c] = iconLabel;
                gridPanel.add(cell);
            }
        }

        paintGrid();

        add(gridPanel, BorderLayout.CENTER);
        pack();
    }

    /*---------------------Build Stats Panel---------------------------
     *      Creates the side panel with counters and pause button
     */

    private JPanel buildStatsPanel() {
        JPanel stats = new JPanel();
        stats.setLayout(new GridLayout(5, 1));

        JButton pauseButton = new JButton("Pause/Play");
        pauseButton.addActionListener(e -> {
            if (timer.isRunning()) {
                timer.stop();
            } else {
                timer.start();
            }
        });

        stats.add(humanLabel);
        stats.add(zombieLabel);
        stats.add(soldierLabel);
        stats.add(generalLabel);
        stats.add(pauseButton);

        updateStats();
        return stats;
    }

    /*-----------------------Update Stats---------------------------
     *          Refreshes the displayed counters for all units
     */

    private void updateStats() {
        humanLabel.setText("Humans: " + model.countHumans());
        zombieLabel.setText("Zombies: " + model.countZombies());
        soldierLabel.setText("Soldiers: " + model.countSoldiers());
        generalLabel.setText("Generals: " + model.countGenerals());
    }

    /*----------------------Paint Grid---------------------------
     *       Updates each tile color and draws entity sprites
     *        based on the current state of the simulation
     */

    public void paintGrid() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                MapTile tile = model.getTile(c, r);
                Entity unit = model.getUnit(c, r);

                gridMap[r][c].setBackground(tile.getColor());

                if (unit == null) {
                    entityIcons[r][c].setIcon(null);
                } else {
                    entityIcons[r][c].setIcon(spriteMap.get(unit.getType()));
                }
            }
        }
    }

    /*-------------------------Load Sprites-------------------------
     *          Loads and stores all unit sprite icons
     */

    private void loadSprites() {
        spriteMap.put(Unit.HUMAN, loadIcon("sprites/human.png"));
        spriteMap.put(Unit.ZOMBIE, loadIcon("sprites/zombie.png"));
        spriteMap.put(Unit.SOLDIER, loadIcon("sprites/soldier.png"));
        spriteMap.put(Unit.GENERAL, loadIcon("sprites/general.png"));
    }

    /*-------------------------Load Icon-------------------------
     *      Loads an image file and scales it to cell size
     */

    private ImageIcon loadIcon(String path) {
        ImageIcon rawIcon = new ImageIcon(path);
        Image scaled = scalePixelArt(rawIcon.getImage(), CELL_SIZE, CELL_SIZE);
        return new ImageIcon(scaled);
    }

    /*----------------------Scale Pixel Art--------------------------
     *       Scales sprite images with nearest-neighbor rendering
     *              so pixel art stays sharp and not blurry
     */

    private Image scalePixelArt(Image src, int width, int height) {
        BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaled.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);

        g2.drawImage(src, 0, 0, width, height, null);
        g2.dispose();

        return scaled;
    }

    /*----------------------Game Over Popup--------------------------
     *         Displays the winner and final surviving counts
     */

    private void showGameOverPopup() {
        String message =
                model.getEndMessage()
                        + "\nHumans: " + model.countHumans()
                        + "\nZombies: " + model.countZombies()
                        + "\nSoldiers: " + model.countSoldiers()
                        + "\nGenerals: " + model.countGenerals();

        JOptionPane.showMessageDialog(
                this,
                message,
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}