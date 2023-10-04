
package frames;
import java.awt.GridLayout;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import database.DatabaseHelper;
import javax.swing.table.DefaultTableCellRenderer;


/** This class creates a nrw Jframe that lists information about all offices in a table
 * @author Sturla*/ 
public class ListOfficesFrame extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;

    public ListOfficesFrame() throws SQLException {
        setTitle("List of offices");
        setSize(800, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout());

        String[] columnNames = {"officeCode",  "phone", "city", "adressLine1", "adressLine2", "state", "country", "postalCode", "territory"};
        tableModel = new DefaultTableModel(columnNames, 0);

        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        
        DatabaseHelper db = new DatabaseHelper();
        db.open();
        ResultSet resultat = db.selectSql("SELECT * FROM offices");
        
        while (resultat.next()) {
        	int officeCode = resultat.getInt("officeCode");
        	String city = resultat.getString("city");
        	String phone = resultat.getString("phone");
        	String adressLine1 = resultat.getString("addressLine1");
        	String adressLine2 = resultat.getString("addressLine2");
        	String state = resultat.getString("state");
        	String country = resultat.getString("country");
        	String postalCode = resultat.getString("postalCode");
        	String territory = resultat.getString("territory");
	  
	        
	        Object[] test = {officeCode, city, phone, adressLine1, adressLine2, state, country, postalCode, territory };
	        tableModel.addRow(test);
        }
        
        db.close();
        setResizable(false);
        setVisible(true);
    
        TableColumnModel columnModel = table.getColumnModel();
        
        
        columnModel.getColumn(0).setHeaderValue("OfficeCode");
        columnModel.getColumn(1).setHeaderValue("City");
        columnModel.getColumn(2).setHeaderValue("Phone");
        columnModel.getColumn(3).setHeaderValue("AddressLine1");
        columnModel.getColumn(4).setHeaderValue("AddressLine2");
        columnModel.getColumn(5).setHeaderValue("State");
        columnModel.getColumn(6).setHeaderValue("Country");
        columnModel.getColumn(7).setHeaderValue("PostalCode");
        columnModel.getColumn(8).setHeaderValue("Territory");
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        
        // Set tooltips for each column
        renderer.setToolTipText("The unique code for the office");
        columnModel.getColumn(0).setCellRenderer(renderer);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("The city where the office is located");
        columnModel.getColumn(1).setCellRenderer(renderer);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("The phone number of the office");
        columnModel.getColumn(2).setCellRenderer(renderer);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("The first line of the office address");
        columnModel.getColumn(3).setCellRenderer(renderer);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("The second line of the office address");
        columnModel.getColumn(4).setCellRenderer(renderer);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("The state where the office is located");
        columnModel.getColumn(5).setCellRenderer(renderer);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("The country where the office is located");
        columnModel.getColumn(6).setCellRenderer(renderer);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("The postal code of the office address");
        columnModel.getColumn(7).setCellRenderer(renderer);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("The territory assigned to the office");
        columnModel.getColumn(8).setCellRenderer(renderer);
    }
}