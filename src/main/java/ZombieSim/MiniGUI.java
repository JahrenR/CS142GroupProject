package ZombieSim;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MiniGUI extends JDialog {

    private JTextField sizeField = new JTextField("20");
    private JTextField civilianField = new JTextField("10");
    private JTextField zombieField = new JTextField("3");
    private JTextField soldierField = new JTextField("2");
    private JTextField generalField = new JTextField("2");

    private ArrayList<Integer> values = null;

    public MiniGUI() {
        setTitle("Zombie Simulation Setup");
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));

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

        JButton submitButton = new JButton("Start Simulation");

        submitButton.addActionListener(e -> validateAndSubmit());

        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(submitButton, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true); // blocks until disposed
    }

    private void validateAndSubmit() {
        try {
            int size = Integer.parseInt(sizeField.getText());
            int civilians = Integer.parseInt(civilianField.getText());
            int zombies = Integer.parseInt(zombieField.getText());
            int soldiers = Integer.parseInt(soldierField.getText());
            int generals = Integer.parseInt(generalField.getText());

            if (size <= 0) {
                showError("Map size must be positive.");
                return;
            }

            int totalUnits = civilians + zombies + soldiers + generals;
            int maxSpots = size * size;

            if (totalUnits > maxSpots) {
                showError("Too many units! Max allowed for this map: " + maxSpots);
                return;
            }

            if (totalUnits == maxSpots) {
                showError("All spaces filled — nobody can move!");
                return;
            }

            values = new ArrayList<>();
            values.add(size);
            values.add(civilians);
            values.add(zombies);
            values.add(soldiers);
            values.add(generals);

            dispose(); // close dialog

        } catch (NumberFormatException ex) {
            showError("Please enter valid integers.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }

    public ArrayList<Integer> getValues() {
        return values;
    }
}