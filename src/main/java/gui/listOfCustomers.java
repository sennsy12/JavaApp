package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.DatabaseHelper;


/**
@author Markus Thoresen
The purpose of this class is to make a GUI that lets you see, write and save all the customers in a list based on what city or state you choose. 
*/
public class listOfCustomers extends JPanel {

    private JLabel selectionLabel;
    private JRadioButton cityRadioButton;
    private JRadioButton stateRadioButton;
    private JComboBox<String> selectionComboBox;
    private JButton writeListButton;
    private static String selectedFolderPath;

    
    /** this is a constructor method including styling options for the radio buttons and the "writelist" button
     *  @author Markus Thoresen is the author of the method
     *  @author Alan Czubak is the author of the styling 
     *  */
    public listOfCustomers() {
        super();
        setLayout(new GridBagLayout());

        selectionLabel = new JLabel("Select: ");
        selectionLabel.setToolTipText("Choose an option");

        cityRadioButton = new JRadioButton("by City");
        cityRadioButton.setToolTipText("Select by city");

        stateRadioButton = new JRadioButton("by State");
        stateRadioButton.setToolTipText("Select by state");

        selectionComboBox = new JComboBox<>();
        selectionComboBox.setToolTipText("Select an item from the list");

        writeListButton = new JButton("Write customer list");
        writeListButton.setToolTipText("Click to write the customer list");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(cityRadioButton);
        buttonGroup.add(stateRadioButton);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.add(cityRadioButton);
        radioPanel.add(stateRadioButton);
        radioPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(selectionLabel, gbc);

        gbc.gridx = 1;
        contentPanel.add(selectionComboBox, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        contentPanel.add(writeListButton, gbc);

        add(radioPanel);
        add(contentPanel);

        cityRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    List<String> cities = getCitiesFromDatabase();
                    updateSelectionComboBox(cities);
                }
            }
        });

        stateRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    List<String> states = getStatesFromDatabase();
                    updateSelectionComboBox(states);
                }
            }
        });

        writeListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    writeCustomerList();
                   
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

   /** This method gives you a combo box list of all the city or states based on your radio button choice, and lets you choose what state or city you want a customer list from
    *  @author Markus Thoresen
    *  */
    
    private List<String> getCitiesFromDatabase() {
        List<String> cities = new ArrayList<>();

        DatabaseHelper db = new DatabaseHelper();
        ResultSet resultSet;
        try {
            db.open();
            resultSet = db.selectSql("SELECT DISTINCT city FROM customers");

            while (resultSet.next()) {
                String city = resultSet.getString("city");
                cities.add(city);
            }

            return cities;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cities;
    }

    private List<String> getStatesFromDatabase() {
        List<String> states = new ArrayList<>();

        DatabaseHelper db = new DatabaseHelper();
        ResultSet resultSet;
        try {
            db.open();
            resultSet = db.selectSql("SELECT DISTINCT state FROM customers");

            while (resultSet.next()) {
                String state = resultSet.getString("state");
                states.add(state);
            }

            return states;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return states;
    }
/** this method populates the combo box with the latest set of items based on the city or state choice
 * @author Markus Thoresen
 * */
    private void updateSelectionComboBox(List<String> items) {
        selectionComboBox.removeAllItems();
        for (String item : items) {
            selectionComboBox.addItem(item);
        }
    }
/**this method writes the customer list after you choose the city or state you want, 
 * then it saves the customerlist to you chosen file path with (that you choose in FileAccessSettings.gui) 
 * @author Markus Thoresen
 * */
    private void writeCustomerList() throws SQLException {
        String selectedItem = (String) selectionComboBox.getSelectedItem();
        System.out.println(selectedItem);
        if (selectedItem == null) {
            JOptionPane.showMessageDialog(this, "Please select a city or state.");
            return;
        }

        String query;
        String fieldName;
        if (cityRadioButton.isSelected()) {
            query = "SELECT * FROM customers WHERE city='" + selectedItem + "'";
            fieldName = "city";
        } else {
            query = "SELECT * FROM customers WHERE state ='" + selectedItem + "'";
            fieldName = "state";
        }

        System.out.println(fieldName);
        DatabaseHelper db = new DatabaseHelper();
        try {
            db.open();
            ResultSet resultSet = db.selectSql(query);

            StringBuilder customerList = new StringBuilder();
            while (resultSet.next()) {
                String customer = resultSet.getString("customerName");
                customerList.append(customer).append("\n");
            }

            if (customerList.length() == 0) {
                JOptionPane.showMessageDialog(this, "No customers found in the selected " + fieldName + ".");
                return;
            }

            String filename = selectedFolderPath + "/customer_list.txt";
            try (FileWriter writer = new FileWriter(filename)) {
                writer.write(customerList.toString());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "An error occurred while writing the customer list, please select folder.");
                e.printStackTrace();
                return;
            }

            JOptionPane.showMessageDialog(this, "Customer list has been written to file successfully.");

            JTextArea textArea = new JTextArea(10, 40);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            JFrame frame = new JFrame("Customer List - " + selectedItem);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            try {
                java.nio.file.Path filePath = java.nio.file.Paths.get(filename);
                java.util.List<String> lines = java.nio.file.Files.readAllLines(filePath);
                for (String line : lines) {
                    textArea.append(line + "\n");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "An error occurred while reading the customer list.");
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while querying the database.");
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
/** see  "public void actionPerformed" in "FileAccessSettings" 
 * @author Markus Thoresen
 * */
    public static void setSelectedFolderPath(String folderPath) {
        try {
			selectedFolderPath = folderPath;
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
/** this method displays the gui, its the entry point of this class and displays the main frame
 * @author Markus Toresen
 * */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("List of Customers");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new listOfCustomers());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}