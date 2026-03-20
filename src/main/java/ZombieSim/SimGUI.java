package ZombieSim;

import ZombieSim.tiles.MapTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

public class SimGUI extends JFrame {

    private static final int ANIMATION_SPEED = 100;
    private static final int CELL_SIZE = 24;

    private final int size;
    private final SimModel model;
    private boolean gameEnded = false;

    private JLabel humanLabel = new JLabel();
    private JLabel zombieLabel = new JLabel();
    private JLabel soldierLabel = new JLabel();
    private JLabel generalLabel = new JLabel();

    JPanel gridPanel = new JPanel();
    JPanel[][] gridMap;
    JLabel[][] entityIcons;

    private final Map<Unit, ImageIcon> spriteMap = new EnumMap<>(Unit.class);

    Timer timer = new Timer(ANIMATION_SPEED, _ -> update());

    public SimGUI(SimModel model) {
        this.model = model;
        this.size = model.getMap().size();

        loadSprites();
        setFrame();
        timer.start();
    }

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

    private void updateStats() {
        humanLabel.setText("Humans: " + model.countHumans());
        zombieLabel.setText("Zombies: " + model.countZombies());
        soldierLabel.setText("Soldiers: " + model.countSoldiers());
        generalLabel.setText("Generals: " + model.countGenerals());
    }

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

    private void loadSprites() {
        spriteMap.put(Unit.HUMAN, loadIcon("sprites/human.png"));
        spriteMap.put(Unit.ZOMBIE, loadIcon("sprites/zombie.png"));
        spriteMap.put(Unit.SOLDIER, loadIcon("sprites/soldier.png"));
        spriteMap.put(Unit.GENERAL, loadIcon("sprites/general.png"));
    }

    private ImageIcon loadIcon(String path) {
        ImageIcon rawIcon = new ImageIcon(path);
        Image scaled = scalePixelArt(rawIcon.getImage(), CELL_SIZE, CELL_SIZE);
        return new ImageIcon(scaled);
    }

    private Image scalePixelArt(Image src, int width, int height) {
        BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaled.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        g2.drawImage(src, 0, 0, width, height, null);
        g2.dispose();

        return scaled;
    }

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