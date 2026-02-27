package ZombieSim;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MiniGUI extends JDialog {

    private JTextField sizeField;
    private JTextField civilianField;
    private JTextField zombieField;
    private JTextField soldierField;
    private JTextField generalField;

    private ArrayList<Integer> list;

    public MiniGUI() {
        setTitle("Simulation Setup");
        setModal(true); // THIS makes it block main thread
        setSize(300, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 5, 5));

        sizeField = new JTextField();
        civilianField = new JTextField();
        zombieField = new JTextField();
        soldierField = new JTextField();
        generalField = new JTextField();

        add(new JLabel("Map Size:"));
        add(sizeField);

        add(new JLabel("Civilians:"));
        add(civilianField);

        add(new JLabel("Zombies:"));
        add(zombieField);

        add(new JLabel("Soldiers:"));
        add(soldierField);

        add(new JLabel("Generals:"));
        add(generalField);

        JButton submitButton = new JButton("Submit");
        add(new JLabel()); // empty cell
        add(submitButton);

        submitButton.addActionListener(e -> submit());

        setVisible(true); // blocks until closed
    }

    private void submit() {
        try {
            int size = Integer.parseInt(sizeField.getText());
            int civilians = Integer.parseInt(civilianField.getText());
            int zombies = Integer.parseInt(zombieField.getText());
            int soldiers = Integer.parseInt(soldierField.getText());
            int generals = Integer.parseInt(generalField.getText());

            list = new ArrayList<>();
            list.add(size);
            list.add(civilians);
            list.add(zombies);
            list.add(soldiers);
            list.add(generals);

            dispose(); // closes dialog
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid integers.");
        }
    }

    public ArrayList<Integer> getList() {
        return list;
    }
}