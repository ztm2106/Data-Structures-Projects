import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TimedPanelUpdate extends JFrame {

    public TimedPanelUpdate(int personNumber, boolean isRed) {
        setTitle("Person " + personNumber);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a JPanel with a green or red background
        JPanel panel = new JPanel();
        Color backgroundColor = isRed ? Color.RED : Color.GREEN;
        panel.setBackground(backgroundColor);

        // Create a label with person information
        JLabel label = new JLabel(isRed ? "?" : "Person " + personNumber);
        label.setForeground(isRed ? Color.BLACK : Color.BLACK);

        // Set the layout manager of the panel to GridBagLayout
        panel.setLayout(new GridBagLayout());

        // Create GridBagConstraints to center the label
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Optional: Set insets for padding

        // Add the label to the panel with the specified constraints
        panel.add(label, gbc);

        // Add the panel to the frame
        add(panel);

        // Make the frame visible
        setVisible(true);
    }

    private static int getRandomRedPanelIndex() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int redPanelIndex = getRandomRedPanelIndex();
            for (int i = 1; i <= 6; i++) {
                new TimedPanelUpdate(i, i == redPanelIndex);
            }

            // Schedule a task to update panels after a minute
            Timer timer = new Timer(30000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updatePanels();
                }
            });
            timer.setRepeats(false); // Only execute the task once
            timer.start();
        });
    }

    private static void updatePanels() {
        for (Frame frame : Frame.getFrames()) {
            if (frame instanceof TimedPanelUpdate) {
                TimedPanelUpdate panelUpdate = (TimedPanelUpdate) frame;
                JPanel panel = (JPanel) panelUpdate.getContentPane().getComponent(0);
                JLabel label = (JLabel) panel.getComponent(0);

                if (panel.getBackground().equals(Color.GREEN)) {
                    // Change green panels to white with label "You are good"
                    panel.setBackground(Color.WHITE);
                    label.setText("You are good");
                    label.setForeground(Color.BLACK);
                } else if (panel.getBackground().equals(Color.RED)) {
                    // Change red panels to black with label "?"
                    panel.setBackground(Color.BLACK);
                    label.setText("?");
                    label.setForeground(Color.WHITE);
                }
            }
        }
    }
}
