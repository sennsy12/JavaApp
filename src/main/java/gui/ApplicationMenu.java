


package gui;

import java.awt.Component;



import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JPopupMenu;
import javax.swing.JFrame;
import javax.swing.JDialog;

import database.DatabaseHelper;
import frames.AboutThisApp;
import frames.ExecuteSql;
import frames.ListAllProducts;
import frames.ModifyEmployeeFrame;
import frames.TestDatabaseFrame;
import gui.FileAccessSettings;
import javax.swing.JFileChooser;





/**This is the class that defines the Menu bar at the top of the application. It is based on the example app ApplicationMenu class and worked on <br>
 * @author Sturla <br>
 * Alan wrote the perform actions */
public class ApplicationMenu extends JMenuBar implements ActionListener {
	
	private JMenu menu_file = null;
	private JMenuItem dBconnectionItem = null;
	private JMenuItem selectFileItem = null;
	private JMenuItem writeCustomersItem = null;
	private JMenuItem bulkImportItem = null;
	private JMenuItem exitItem = null;
	
	private JMenu menu_database = null;
	private JMenuItem testDatabaseItem = null;
	private JMenuItem executeSqlItem = null;
	private JMenuItem addOrModifyItem = null;
	private JMenuItem listAllProductsItem = null;
	private JMenuItem filterAndPresentItem = null;
	
	private JMenu menu_help = null;
	private JMenuItem option_tip = null;
	
	private Font bigFont = new Font("Calibri", Font.PLAIN, 28);
	private Font smallFont = new Font("Calibri", Font.PLAIN, 24);
	
	protected ApplicationMenu() {
		displayMenuBar();
	}
	
	
	
	
	
	protected void displayMenuBar() {
		UIManager.put("Menu.font", bigFont);
		
		int smallFontSize = 15;
	    Font smallFont = new Font("Calibri", Font.PLAIN, smallFontSize);
		UIManager.put("MenuItem.font", smallFont);
		
		//Defines file menu
		menu_file = new JMenu("File");
		
		
		selectFileItem = new JMenuItem("Select file");
		selectFileItem.addActionListener(this);
		
		writeCustomersItem = new JMenuItem("Write customers into file");
		writeCustomersItem.addActionListener(this);
		
		bulkImportItem = new JMenuItem("Bulk import of orders");
		bulkImportItem.addActionListener(this);
		
		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(this);

		
	
		
		
		
		//Adds the items to the file menu
		menu_file.add(selectFileItem);
		menu_file.add(writeCustomersItem);
		menu_file.add(bulkImportItem);
		menu_file.add(exitItem);
		
		//Define the "Database" menu
		menu_database = new JMenu("Database");
		
		testDatabaseItem = new JMenuItem("Test database connection");
		testDatabaseItem.addActionListener(this);
		
		executeSqlItem = new JMenuItem("Execute SQL query");
		executeSqlItem.addActionListener(this);
		
		addOrModifyItem = new JMenuItem("Add or modify employee");
		addOrModifyItem.addActionListener(this);
		
		listAllProductsItem = new JMenuItem("List all products");
		listAllProductsItem.addActionListener(this);
		
		filterAndPresentItem = new JMenuItem("Filter and present offices from a country");
		filterAndPresentItem.addActionListener(this);
		
		
		
		//Items added to the "Database" menu
		menu_database.add(testDatabaseItem);
		menu_database.add(executeSqlItem);
		menu_database.add(addOrModifyItem);
		menu_database.add(listAllProductsItem);
		menu_database.add(filterAndPresentItem);
		
		//Defines the "Help" menu
		menu_help = new JMenu("Help");
		
		option_tip = new JMenuItem("About");
		option_tip.addActionListener(this);
		menu_help.add(option_tip);
		
		//Adds the menus
		this.add(menu_file);
		this.add(menu_database);
		this.add(menu_help);
	
	
	selectFileItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	        JFileChooser fileChooser = new JFileChooser();
	        int returnValue = fileChooser.showOpenDialog(null);
	        
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            // Handle the selected file here
	        }
	    }
	});}
	
	
	
	
	// Perform actions
	public void actionPerformed(ActionEvent event) {
	    String arg = event.getActionCommand();


	    if (arg.equals("Test database connection")) {
	        new TestDatabaseFrame();
	    } else if (arg.equals("Exit")) {
	        System.exit(0);
	    } else if (arg.equals("About")) {
	        AboutThisApp frame = new AboutThisApp();
	        frame.showMessage();
	    } else if (arg.equals("List all products")) {
	        try {
	            ListAllProducts frame = new ListAllProducts();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    } else if (arg.equals("Add or modify employee")) {
	        ModifyEmployeeFrame frame = new ModifyEmployeeFrame();
	        
	    }
	    
	    selectFileItem.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            JFileChooser fileChooser = new JFileChooser();
	            int returnValue = fileChooser.showOpenDialog(null);
	            
	            if (returnValue == JFileChooser.APPROVE_OPTION) {
	                File selectedFile = fileChooser.getSelectedFile();
	               
	            }
	        }
	    });
	    

		 if (arg.equals("Bulk import of orders")) {
			OrderImportGUI order = new OrderImportGUI();
		}
		 else if (arg.equals("Write customers into file")) {
		        listOfCustomers gui = new listOfCustomers();
		        JFrame frame = new JFrame();
		        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        frame.getContentPane().add(gui);
		        frame.pack();
		        frame.setLocationRelativeTo(null); 
		        frame.setVisible(true);

		    }
		    
		  
		    else if (arg.equals("Execute SQL query")) {
		        JMenuItem menuItem = (JMenuItem) event.getSource();
		        JPopupMenu popupMenu = (JPopupMenu) menuItem.getParent();
		        Component invoker = popupMenu.getInvoker();
		        JFrame parentFrame = (JFrame) SwingUtilities.getRoot(invoker);
		        ExecuteSql frame = new ExecuteSql();
		        frame.setVisible(true);
		        frame.setLocationRelativeTo(parentFrame);
		    }
		}
		


	// Method that displays option pane
	private void displayMessage(String message) {
		UIManager.put("OptionPane.messageFont", bigFont);
		UIManager.put("OptionPane.buttonFont", bigFont);
		JOptionPane.showMessageDialog(this, message);
	}
	
	
}









