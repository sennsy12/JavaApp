

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**This is a database class that handles the database credentials and communicates with the database
 * It is based on the example app DatabaseHandler, and then modified and worked on
 * @author Sturla,<br> 
 * 
 *  A PreparedStatement function added by @abdiwahab,<br>*/

public class DatabaseHelper implements DatabaseInterface {
	
	// JDBC driver name and database URL
    private final String DB_URL = "jdbc:mysql://localhost/classicmodels";
    
    //  Database credentials
    private static final String USER = "student";
    private static final String PASS = "student";
    
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rSet = null;
    private PreparedStatement myStmt = null;
	

	
	@Override
    public void open() throws SQLException{
        try {
            //Establish connection
            conn = DriverManager.getConnection(DB_URL, USER,PASS);
            //Create statement that will be used for executing SQL queries
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();// More elegant solutions for catching errors exist but they are out of the scope for this course
        }
    }
    
    @Override
    public void close() throws SQLException{
        try {
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
    }
    
    @Override
    public void test() throws SQLException{
        try {
            String sql;
            sql = "SELECT * FROM employees";
            ResultSet rs = stmt.executeQuery(sql); // DML
            // stmt.executeUpdate(sql); // DDL
            
            //STEP 5: Extract data from result set
            while(rs.next()){
                //Display values
            	System.out.println(rs.getString("lastName") + ", " + rs.getString("firstName"));
            }
            //STEP 6: Clean-up environment
            rs.close();
        } catch (SQLException ex) {
        	ex.printStackTrace(); 
        }
    }
    
    /** Method that defines preparedStatement query to OrderImportGUI 
     * JAVA CLASS. method will be called to that class 
    *  @author Abdiwahab Mohamed <br>*/
    public PreparedStatement prepareStatement(String query) throws SQLException {
        return conn.prepareStatement(query);
    }
    
    public ResultSet selectSql(String sql) throws SQLException {
    	try {
			ResultSet rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    
    /** This method takes in a number of parameters, prepares an sql statement and inserts an employee to the database*/
   public void insertEmployee(int employeeNumber, String lastName, String firstName, String extension, String email, int officeCode, int reportsTo, String jobTitle) throws SQLException {
   	try {
		String query = "insert into employees " + "(employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle) " + "values "
				+ "(?, ?, ?, ?, ?, ?, ?, ?)";
		 myStmt = conn.prepareStatement(query);

		// 3. Set the parameters
		myStmt.setInt(1, employeeNumber);
		myStmt.setString(2, lastName);
		myStmt.setString(3, firstName);
		myStmt.setString(4, extension);
		myStmt.setString(5, email);
		myStmt.setInt(6, officeCode);
		myStmt.setInt(7, reportsTo);
		myStmt.setString(8, jobTitle);
		
		myStmt.execute();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   
   /** This method takes in a number of parameters, prepares an sql statement and updates an employee to the database*/
   public void updateEmployee(int employeeNumber, String lastName, String firstName, String extension, String email, int officeCode, int reportsTo, String jobTitle) throws SQLException {
	   	try {
			String query = "update employees set lastName = ?, firstName = ?, extension = ?, email = ?, officeCode = ?, reportsTo = ?, jobTitle = ? where employeeNumber = ?;";
			 myStmt = conn.prepareStatement(query);

			// 3. Set the parameters
			myStmt.setString(1, lastName);
			myStmt.setString(2, firstName);
			myStmt.setString(3, extension);
			myStmt.setString(4, email);
			myStmt.setInt(5, officeCode);
			myStmt.setInt(6, reportsTo);
			myStmt.setString(7, jobTitle);
			myStmt.setInt(8, employeeNumber);
			
			myStmt.execute();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
   /** This method takes in an employee number and deletes that employee in the database*/
   public void deleteEmployee(int employeeNumber) throws SQLException {
	   	try {
			String query = "delete from employees where employeeNumber = ?;";
			 myStmt = conn.prepareStatement(query);

			// 3. Set the parameters

			myStmt.setInt(1, employeeNumber);
			
			myStmt.execute();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
   
   	
	
}
