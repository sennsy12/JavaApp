

package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import database.DatabaseHelper;

/** This is the main content in the gui. It is based on the example app, and then worked on and further modified
 * @author Sturla*/
public class MainContent extends JPanel {

    private Font bigFont = new Font("Calibri", Font.PLAIN, 40);
    private Font smallFont = new Font("Calibri", Font.PLAIN, 24);
    private DatabaseHelper dbHelper = new DatabaseHelper();

    public MainContent() {
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Create an instance of PanelMenu
        PanelMenu panelMenu = new PanelMenu();

        // Specify the constraints for the PanelMenu
        c.fill = GridBagConstraints.VERTICAL; // This component should fill its display area vertically
        c.weightx = 0.2; // Specify the relative width of the PanelMenu
        c.weighty = 1.0; // Request any extra vertical space
        c.gridx = 0; // Place PanelMenu in the first column

        // Add the PanelMenu to the panel with the constraints
        this.add(panelMenu, c);

        // Create an instance of FileAccessSettings
        FileAccessSettings fileAccessSettings = new FileAccessSettings();
        fileAccessSettings.setBorder(BorderFactory.createTitledBorder("File Access Settings")); // Add a titled border

        // Specify the constraints for the FileAccessSettings
        c.fill = GridBagConstraints.BOTH; // This component should fill its display area
        c.weightx = 0.8; // Specify the relative width of the FileAccessSettings
        c.weighty = 0.5; // Request any extra vertical space
        c.gridx = 1; // Place FileAccessSettings in the second column

        
        ReportsAndUpdates reportsAndUpdates = new ReportsAndUpdates ();
        reportsAndUpdates.setBorder(BorderFactory.createTitledBorder("Reports and updates")); // Add a titled border
        
       

     
        // Specify the constraints for the FileAccessSettings
        c.fill = GridBagConstraints.BOTH; // This component should fill its display area
        c.weightx = 0.8; // Specify the relative width of the FileAccessSettings
        c.weighty = 0; // Request any extra vertical space
        c.gridx = 2; // Place FileAccessSettings in the second column
        
        // create instance of lifOfCustomers
        listOfCustomers listOfCustomers = new listOfCustomers();
        listOfCustomers.setBorder(BorderFactory.createTitledBorder("List of customers"));
        
        c.fill = GridBagConstraints.BOTH; 
        c.weightx = 0.8; 
        c.weighty = 0; 
        c.gridx = 3; 
        
        
        // Add the FileAccessSettings to the panel with the constraints
        this.add(fileAccessSettings, c);
        this.add(listOfCustomers, c);
        this.add(reportsAndUpdates, c);
        

        
    }
}