package frames;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a JFrame that displays information about the application.
 * It includes a message label and a close button.
 * 
 * @author Arash
 */
public class AboutThisApp extends JFrame {

    /**
     * Creates an instance of AboutThisApp.
     * Sets up the frame with a title, size, and close operation.
     * Adds a message label and a close button to the panel.
     */
    public AboutThisApp() {
        setTitle("About the App");
        setSize(1000, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel messageLabel = new JLabel("Hi, this is the application for the OBJ2100 exam. This application was made by 6 Students, hope you enjoy");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(messageLabel, gbc);

        JButton closeButton = new JButton("Close");
        gbc.gridy = 1;
        panel.add(closeButton, gbc);

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(panel);
    }

    /**
     * Displays the AboutThisApp frame.
     */
    public void showMessage() {
        setVisible(true);
    }
}