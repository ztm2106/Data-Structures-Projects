import javax.swing.*;
import java.awt.*;

public class GreenRectangle extends JFrame {

    public GreenRectangle() {
        setTitle("Green Rectangle");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a JPanel with a green background
        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);

        // Create a label and set its position to the center using GridBagLayout
        JLabel label = new JLabel("Centered Label");
        label.setForeground(Color.BLACK);

        // Set the layout manager of the panel to GridBagLayout
        panel.setLayout(new GridBagLayout());

        // Create GridBagConstraints to center the label
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0); // Optional: Set insets for padding

        // Add the label to the panel with the specified constraints
        panel.add(label, gbc);

        // Add the panel to the frame
        add(panel);

        // Make the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GreenRectangle();
        });
    }
}