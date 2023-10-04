

package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DatabaseHelper;
import frames.ListOfficesFrame;
import frames.TestDatabaseFrame;
import frames.AboutThisApp;
import frames.ExecuteSql;


/** This class defines the panel of buttons to the left of the gui
 * @author Sturla <br>
 * Alan, tooltips and button appearence <br>
 * Aleksander worked on layout */
public class PanelMenu extends JPanel implements ActionListener {

    private JButton button1, button2, button3, button4;

    public PanelMenu() {
        super();
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));

     
        button1 = createButton("Test database connection");
        button2 = createButton("Execute SQL query");
        button3 = createButton("About the app");
        button4 = createButton("Exit application");
        
        button1.setToolTipText("Click to test database connection");
        button2.setToolTipText("Click to execute an SQL query");
        button3.setToolTipText("Click for information about the app");
        button3.setToolTipText("Click to exit the application");

        
        
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);
        

       
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;  
        c.insets = new Insets(1, 3, 3, 3);

        
        c.gridx = 0;
        c.gridy = 0;
        add(button1, c);

       
        c.gridy = 1;
        add(button2, c);

        
        c.gridy = 2;
        add(button3, c);

        
        c.gridy = 3;
        add(button4, c);
        
        ToolTipManager.sharedInstance().setInitialDelay(5);
        ToolTipManager.sharedInstance().setDismissDelay(10000);
    }

    		@Override
    	    public void actionPerformed(ActionEvent e) {
    	        
    	    	
    	    	if (e.getSource() == button1) {
    	    	    new TestDatabaseFrame();
    	    	}
   	        
    	        if (e.getSource() == button4) {
    	            System.out.println("Exit button clicked");
    	            System.exit(0);
    	     
    	        }
    	        
    	        /** 
    	        @author Arash
    	        * ActionListener implementation for button actions.
 				* Button2 creates and shows an instance of the ExecuteSql frame.
 				* Button3 creates and shows an instance of the AboutThisApp frame.
    	        */
    	        if (e.getSource() == button2) {
    	        	
    	            ExecuteSql executeSql = new ExecuteSql();
    	            executeSql.setVisible(true);
    	        }
    	        
    	        if (e.getSource() == button3) {
    	            
    	            AboutThisApp aboutThisApp = new AboutThisApp();
    	            aboutThisApp.showMessage();
    	        }
    	    }
    	
    

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Calibri", Font.PLAIN, 19));
        button.setPreferredSize(new Dimension(250, 50));
        return button;
    }
} 
    
    
    
    
    









