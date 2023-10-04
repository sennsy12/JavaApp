package database;

import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of the methods for establishing database connection
 */

public interface DatabaseInterface {
	
	//	open connection
	void open() throws SQLException;
	
	// close connection
	void close() throws SQLException;
	
	// test connection
	void test() throws SQLException;
	
	// get list of all employees
	//List<Employee> getEmployees() throws SQLException;
	
	
	
}