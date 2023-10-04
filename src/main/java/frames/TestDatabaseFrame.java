		/**
 * A JFrame that is used to verify the database connection is represented by the TestDatabaseFrame class.
 * 
 * It provides a visual interface for testing the functionality of the DatabaseHelper class by
   establishing a database connection and performing a test also displaying the result to the user.
 * 
 * Alan made this frame
 * 
 * The purpose of this class is to provide a visual interface for testing the functionality of the DatabaseHelper class.
   It allows the user to see whether the database connection is successful or not.
 * 
 * The included methods in this class are:
   TestDatabaseFrame(): Constructor that initializes the JFrame and tests the database connection.
   Note: This class relies on the DatabaseHelper class for database-related operations.

package frames;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import database.DatabaseHelper;

public class TestDatabaseFrame extends JFrame 

 */

package frames;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import database.DatabaseHelper;
import gui.PanelMenu;

public class TestDatabaseFrame extends JFrame {

    public TestDatabaseFrame() {
        super("Test Database Connection");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());

        String result;

        try {
            DatabaseHelper db = new DatabaseHelper();
            db.open();
            db.test();
            db.close();
            result = "Connection successful";
        } catch (Exception ex) {
            result = "Connection not successful";
        }

        JLabel label = new JLabel(result);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.CENTER;
        mainPanel.add(label, constraints);

        add(mainPanel);

        setSize(300, 120); // Set the desired size

        setLocationRelativeTo(null);
        setVisible(true);
    }
}




