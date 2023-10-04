/** 
 * This is a class that lists all products that we have on our database in a table form.
 *  @author Abdiwahab Mohamed <br>*/

package frames;

import javax.swing.*;

import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DatabaseHelper;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.DefaultTableCellRenderer;



public class ListAllProducts extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    
    /**A constructor of the CLASS */
    public ListAllProducts() throws SQLException {
        setTitle("List of Products");
        setSize(700, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout());
        
       /**an array that lists all products*/
        
        String[] columnNames = {"Product Code",  "Product Name", "Product Line", "Product Scale", "Product Vendor", "Product Description", "Quantity In Stock", "Buy Price", "MSRP"};
        
        
        tableModel = new DefaultTableModel(columnNames, 0); 
        
        // table that will contain productlist
        table = new JTable(tableModel);
        
        /** this allows to scroll you up and down the table*/
        JScrollPane scrollPane = new JScrollPane(table);

        
        this.add(scrollPane);
        
        /** Here I instantiate the datasehelper 
         * constructor to select the list of products*/
        
        DatabaseHelper db = new DatabaseHelper();
        db.open();
        ResultSet resultset = db.selectSql("SELECT * FROM products");
        
        while (resultset.next()) {
        	String productCode = resultset.getString("productCode");
        	String productName = resultset.getString("productName");
        	String productLine = resultset.getString("productLine");
        	String productScale = resultset.getString("productScale");
        	String productVendor = resultset.getString("productVendor");
        	String productDescription = resultset.getString("productDescription");
        	int quantityInStock = resultset.getInt("quantityInStock");
        	String buyPrice = resultset.getString("buyPrice");
        	String MSRP = resultset.getString("MSRP");
	        
	        Object[] products = {productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP };
	        tableModel.addRow(products);
        }
        
        db.close();
        setResizable(true);
        setVisible(true);
 
  
        /**
         * here defines the structure that will be shown on table.
         * */
        TableColumnModel columnModel = table.getColumnModel();
        
        columnModel.getColumn(0).setHeaderValue("Product Code");
        columnModel.getColumn(1).setHeaderValue("Product Name");
        columnModel.getColumn(2).setHeaderValue("Product Line");
        columnModel.getColumn(3).setHeaderValue("Product Scale");
        columnModel.getColumn(4).setHeaderValue("Product Vendor");
        columnModel.getColumn(5).setHeaderValue("Product Description");
        columnModel.getColumn(6).setHeaderValue("Quantity In Stock");
        columnModel.getColumn(7).setHeaderValue("Buy Price");
        columnModel.getColumn(8).setHeaderValue("MSRP");
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        
       
        renderer.setToolTipText("Code of the product");
        columnModel.getColumn(0).setCellRenderer(renderer);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Name of the product");
        columnModel.getColumn(1).setCellRenderer(renderer);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Line of the product");
        columnModel.getColumn(2).setCellRenderer(renderer);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Scale of the product");
        columnModel.getColumn(3).setCellRenderer(renderer);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Vendor of the product");
        columnModel.getColumn(4).setCellRenderer(renderer);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Description of the product");
        columnModel.getColumn(5).setCellRenderer(renderer);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Quantity in stock");
        columnModel.getColumn(6).setCellRenderer(renderer);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Price at which the product is bought");
        columnModel.getColumn(7).setCellRenderer(renderer);
        
        renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Manufacturer's suggested retail price");
        columnModel.getColumn(8).setCellRenderer(renderer);
    }
}