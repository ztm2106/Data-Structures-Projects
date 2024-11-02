import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class TimedPanelWithDBMeter extends JFrame {

    private static final int SAMPLE_RATE = 44100;
    private static final int CHUNK_SIZE = 1024;

    private JLabel dbLabel;

    public TimedPanelWithDBMeter(int personNumber, boolean isRed) {
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

        // Create a label for the dB meter
        dbLabel = new JLabel("dB: 0");
        dbLabel.setForeground(Color.BLACK);
        dbLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Set the layout manager of the panel to GridBagLayout
        panel.setLayout(new GridBagLayout());

        // Create GridBagConstraints to center the labels
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Optional: Set insets for padding

        // Add the labels to the panel with the specified constraints
        panel.add(label, gbc);

        gbc.gridy = 1;
        panel.add(dbLabel, gbc);

        // Add the panel to the frame
        add(panel);

        // Make the frame visible
        setVisible(true);

        // Start the microphone thread
        startMicrophoneThread();
    }

    private void startMicrophoneThread() {
        new Thread(() -> {
            try {
                AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, true);
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

                if (!AudioSystem.isLineSupported(info)) {
                    System.err.println("Line not supported");
                    System.exit(0);
                }

                TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();

                byte[] buffer = new byte[CHUNK_SIZE];
                int bytesRead;

                while (true) {
                    bytesRead = line.read(buffer, 0, buffer.length);
                    if (bytesRead > 0) {
                        // Calculate dB level
                        double rms = calculateRMSLevel(buffer);
                        double db = 20 * Math.log10(rms / 32767.0 + 1e-6);
                        SwingUtilities.invokeLater(() -> updateDBLabel(db));
                    }
                }
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private double calculateRMSLevel(byte[] audioData) {
        long sum = 0;
        for (byte anAudioData : audioData) {
            sum += anAudioData * anAudioData;
        }
        double average = sum / (double) audioData.length;
        return Math.sqrt(average);
    }

    private void updateDBLabel(double db) {
        dbLabel.setText(String.format("dB: %.2f", db));
    }

    private static int getRandomRedPanelIndex() {
        return new Random().nextInt(6) + 1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int redPanelIndex = getRandomRedPanelIndex();
            for (int i = 1; i <= 6; i++) {
                new TimedPanelWithDBMeter(i, i == redPanelIndex);
            }

            Timer timer = new Timer(10000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updatePanels();
                }
            });
            timer.setRepeats(false);
            timer.start();
        });
    }

    private static void updatePanels() {
        for (Frame frame : Frame.getFrames()) {
            if (frame instanceof TimedPanelWithDBMeter) {
                TimedPanelWithDBMeter panelUpdate = (TimedPanelWithDBMeter) frame;
                JPanel panel = (JPanel) panelUpdate.getContentPane().getComponent(0);
                JLabel label = (JLabel) panel.getComponent(0);

                if (panel.getBackground().equals(Color.GREEN)) {
                    panel.setBackground(Color.WHITE);
                    label.setText("You are good");
                    label.setForeground(Color.BLACK);
                } else if (panel.getBackground().equals(Color.RED)) {
                    panel.setBackground(Color.BLACK);
                    label.setText("?");
                    label.setForeground(Color.WHITE);
                }
            }
        }
    }
}
