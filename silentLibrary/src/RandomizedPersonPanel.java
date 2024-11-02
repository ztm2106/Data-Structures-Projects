import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class RandomizedPersonPanel extends JFrame {

    public RandomizedPersonPanel(int personNumber, boolean isRed) {
        setTitle("Person " + personNumber);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a JPanel with a green or red background
        JPanel panel = new JPanel();
        Color backgroundColor = isRed ? Color.RED : Color.GREEN;
        panel.setBackground(backgroundColor);

        // Create a label with person information
        JLabel label = new JLabel(isRed ? "x" : "Person " + personNumber);
        label.setForeground(isRed ? Color.WHITE : Color.BLACK);

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
                new RandomizedPersonPanel(i, i == redPanelIndex);
            }
        });
    }
}
