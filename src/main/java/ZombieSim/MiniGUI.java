package ZombieSim;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * MiniGUI represents the setup dialog for the Zombie Simulation.
 * <p>
 * It allows the user to configure:
 * <ul>
 *     <li>Map size</li>
 *     <li>Number of civilians</li>
 *     <li>Number of zombies</li>
 *     <li>Number of soldiers</li>
 *     <li>Number of generals</li>
 * </ul>
 * <p>
 * The class handles input validation, loading and saving configurations,
 * and returning values to the main simulation application.
 */

public class MiniGUI extends JDialog {

    //Input fields for user configuration (default values provided) //

    private JTextField sizeField = new JTextField("30");
    private JTextField civilianField = new JTextField("20");
    private JTextField zombieField = new JTextField("2");
    private JTextField soldierField = new JTextField("2");
    private JTextField generalField = new JTextField("1");

    private JSlider speedSlider = new JSlider(50, 1000, 500);
    private JLabel speedLabel = new JLabel("Speed: 500 ms");

    //Stores selected map file path (if user chooses one)
    //Stores validates values to pass back to main simulation

    private String mapFile = null;
    private List<Integer> values = null;

    private JLabel mapFileLabel = new JLabel("No map file selected");

    /**
     * Constructs the MiniGUI dialog and displays it modally.
     * The dialog blocks the main application until closed.
     */

    public MiniGUI() {

        setTitle("Zombie Simulation Setup");

        // Makes this a modal dialog — blocks main program until closed
        setModal(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null);

        /*
         * Main panel uses BorderLayout
         * Adds spacing between components.
         */
        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        /*
         * GridLayout arranges labels and text fields
         * into a clean 2-column structure.
         */
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        // Label and text field pairs
        inputPanel.add(new JLabel("Map Size:"));
        inputPanel.add(sizeField);

        inputPanel.add(new JLabel("Civilians:"));
        inputPanel.add(civilianField);

        inputPanel.add(new JLabel("Zombies:"));
        inputPanel.add(zombieField);

        inputPanel.add(new JLabel("Soldiers:"));
        inputPanel.add(soldierField);

        inputPanel.add(new JLabel("Generals:"));
        inputPanel.add(generalField);

        inputPanel.add(new JLabel("Speed:"));
        inputPanel.add(speedSlider);

        inputPanel.add(new JLabel("Map file:"));
        inputPanel.add(mapFileLabel);

        /*
         * Submit button starts validation process.
         */
        JButton submitButton = new JButton("Start Simulation");

        submitButton.addActionListener(e -> validateAndSubmit());
        JButton loadButton = new JButton("Load Map File");
        loadButton.addActionListener(e -> chooseMapFile());
        JButton loadConfigButton = new JButton("Load Config");
        JButton saveButton = new JButton("Save Config");

        JButton resetButton = new JButton("Reset Defaults");
        resetButton.addActionListener(e -> resetDefaults());

        sizeField.addActionListener(e -> validateAndSubmit());
        civilianField.addActionListener(e -> validateAndSubmit());
        zombieField.addActionListener(e -> validateAndSubmit());
        soldierField.addActionListener(e -> validateAndSubmit());
        generalField.addActionListener(e -> validateAndSubmit());

        loadConfigButton.addActionListener(e -> {
            try {
                JFileChooser chooser = new JFileChooser();
                int result = chooser.showOpenDialog(this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    Scanner scanner = new Scanner(file);

                    sizeField.setText("" + scanner.nextInt());
                    civilianField.setText("" + scanner.nextInt());
                    zombieField.setText("" + scanner.nextInt());
                    soldierField.setText("" + scanner.nextInt());
                    generalField.setText("" + scanner.nextInt());

                    scanner.close();
                }

            } catch (Exception ex) {
                showError("Could not load config.");
            }
        });

        saveButton.addActionListener(e -> {
            try {
                List<Integer> temp = new ArrayList<>();
                temp.add(Integer.parseInt(sizeField.getText()));
                temp.add(Integer.parseInt(civilianField.getText()));
                temp.add(Integer.parseInt(zombieField.getText()));
                temp.add(Integer.parseInt(soldierField.getText()));
                temp.add(Integer.parseInt(generalField.getText()));


                JFileChooser chooser = new JFileChooser();
                chooser.setSelectedFile(new File("config.txt"));
                int result = chooser.showSaveDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    saveConfig(file.getAbsolutePath(), temp);

                    JOptionPane.showMessageDialog(this, "Config saved successfully.)", "Success", JOptionPane.INFORMATION_MESSAGE);
                }

                JOptionPane.showMessageDialog(
                        this,
                        "Configuration saved successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (NumberFormatException ex) {
                showError("Invalid input. Cannot save.");
            }
        });


        // Assemble layout
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new GridLayout(3,2,10,10));
        buttonPanel.add(submitButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadConfigButton);
        buttonPanel.add(resetButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        pack();
        setLocationRelativeTo(null);

        /*
         * setVisible(true) makes the dialog appear.
         * Because it is modal, execution pauses here
         * until the window is closed.
         */
        setVisible(true);
    }

    /*
     * ========================= Validation Logic =========================
     *
     * This method:
     *   1. Converts text input into integers
     *   2. Checks for invalid input
     *   3. Ensures map constraints are satisfied
     *   4. Stores values if everything is valid
     */
    private void validateAndSubmit() {

        try {

            // Convert text fields to integers
            int size = Integer.parseInt(sizeField.getText());
            int civilians = Integer.parseInt(civilianField.getText());
            int zombies = Integer.parseInt(zombieField.getText());
            int soldiers = Integer.parseInt(soldierField.getText());
            int generals = Integer.parseInt(generalField.getText());

            /*
             * Rule 1:
             * Map size must be positive.
             */
            if (size <= 0) {
                showError("Map size must be positive.");
                return;
            }

            /*
             * Rule 2:
             * Total number of entities cannot exceed
             * total grid spaces.
             */
            int totalUnits = civilians + zombies + soldiers + generals;
            int maxSpots = size * size;

            if (totalUnits > maxSpots) {
                showError("Too many units! Max allowed for this map: " + maxSpots);
                return;
            }

            /*
             * Rule 3:
             * Map cannot be completely full,
             * otherwise nobody can move.
             */
            if (totalUnits == maxSpots) {
                showError("All spaces filled — nobody can move!");
                return;
            }

            /*
             * If all validation passes,
             * store results in a list.
             */
            values = new ArrayList<>();
            values.add(size);
            values.add(civilians);
            values.add(zombies);
            values.add(soldiers);
            values.add(generals);

            /*
             * Close dialog.
             * Main program resumes after this.
             */
            dispose();

        } catch (NumberFormatException ex) {

            /*
             * If user types letters or invalid numbers,
             * show an error message.
             */
            showError("Please enter valid integers.");
        }
    }

    public String getMapFile() {
        return mapFile;
    }

    private void chooseMapFile() {

        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            File selectedFile = chooser.getSelectedFile();

            mapFile = selectedFile.getAbsolutePath();
            mapFileLabel.setText(selectedFile.getName());

        }
    }

    private void resetDefaults() {
        sizeField.setText("0");
        civilianField.setText("0");
        zombieField.setText("0");
        soldierField.setText("0");
        generalField.setText("0");
        mapFile = null;
        mapFileLabel.setText("No map file selected");
    }

    /*
     * Displays a pop-up error message.
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public void saveConfig(String filename, List<Integer> values) {
        try {
            PrintWriter out = new PrintWriter(filename);
            for (int v : values) {
                out.println(v);
            }
            out.close();
        } catch (Exception e) {
            showError("Failed to save file.");
        }
    }

    /*
     * Returns the validated values.
     * Used by MainApp to start the simulation.
     */
    public List<Integer> getValues() {
        return values;
    }
}