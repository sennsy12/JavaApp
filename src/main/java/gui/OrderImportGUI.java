
/** 
 * The CLASS would allow you importing procts
orders from a csv file to the database..
 *  @author Abdiwahab Mohamed <br>*/



package gui;
import database.DatabaseHelper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class OrderImportGUI extends JFrame {
    private JButton importButton;

    /**A constructor the CLASS */
    public OrderImportGUI() {
    	
        /** Here  instantiates the ImportButton and gives import orders name*/
    	
        importButton = new JButton("Import Orders");
        
        importButton.addActionListener(new ActionListener() {
        	
        	 /** This method defines JfileChooser that will popUp on 
        	  * dialogbox approves files that a chosen */
        	
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        importOrders(selectedFile);
                    } catch (SQLException ex) {
                        Logger.getLogger(OrderImportGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(OrderImportGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        add(importButton);
        setTitle("Order Import");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
	 /** This method allow you to import csvfile that contains the list of products */
    
    private void importOrders(File csvFile) throws SQLException, IOException {
    	
    	   /** Here I instantiate the datasehelper 
         * construction to insert of  all products*/
        DatabaseHelper db = new DatabaseHelper();
        try {
        db.open();
        
        String query = "INSERT INTO products (productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = db.prepareStatement(query);
        
        /** The bufferReader reads a csvFile that we want to send to databases*/
        
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length != 9)
                continue;
            String productCode = data[0];                
            String productName = data[1];                
            String productLine = data[2];                
            String productScale = data[3];                
            String productVendor = data[4];                
            String productDescription = data[5];
            int quantityInStock = Integer.parseInt(data[6]);
            double buyPrice = Double.parseDouble(data[7]);
            double MSRP = Double.parseDouble(data[8]);
            
            statement.setString(1, productCode);
            statement.setString(2, productName);                
            statement.setString(3, productLine);                
            statement.setString(4, productScale);                
            statement.setString(5, productVendor);                
            statement.setString(6, productDescription);
            statement.setInt(7, quantityInStock);
            statement.setDouble(8, buyPrice);                
            statement.setDouble(9, MSRP);
            
            statement.executeUpdate();
        }
        
        br.close();
        statement.close();
        db.close();
        String message = "Orders imported successfully";
        JOptionPane.showMessageDialog(this, message);} catch (SQLException e) {
            String message = "Error: Whoops! There seems to be a problem with your Data. order not imported";
            JOptionPane.showMessageDialog(this, message);
			e.printStackTrace();
		}

        
    }
}
