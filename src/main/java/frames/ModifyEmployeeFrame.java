
package frames;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import database.DatabaseHelper;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/** 
 * This is a class that handles update, create and deletion of employees.
 *  @author Sturla <br>*/
public class ModifyEmployeeFrame extends JFrame implements ActionListener {
    JTextField employeeNumberField, lastNameField, firstNameField, extensionField, officeCodeField, reportsToField, jobTitleField, emailField;
    JLabel employeeNumberLabel, lastNameLabel, firstNameLabel, extensionLabel, officeCodeLabel, reportsToLabel, jobTitleLabel, emailLabel;
    JButton modifyButton;
    JButton deleteButton;
    JButton submitButton;
    JComboBox<Integer> officeCodeComboBox;
    JComboBox<Integer> employeeNumberComboBox;
    JComboBox<Integer> reportsToComboBox;
    
    /**A constructor that creates the frame */
    public ModifyEmployeeFrame() {
        super("Modify employee");
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,10,10,10);

        employeeNumberLabel = new JLabel("Employee Number");
        employeeNumberLabel.setToolTipText("Select the employee number");
        c.gridx = 0;
        c.gridy = 0;
        add(employeeNumberLabel, c);
        employeeNumberComboBox = new JComboBox<>();
        c.gridx = 1;
        add(employeeNumberComboBox, c);
        
        List<Integer> employeeNumberList = getEmployeeNumberFromDatabase();
        updateEmployeeNumberComboBox(employeeNumberList);
        
        //Item listener that runs each time employeeNumber changes
        employeeNumberComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	
                	DatabaseHelper db = new DatabaseHelper();
                	try {
						db.open();
						ResultSet resultSet = db.selectSql("SELECT * from employees where employeeNumber = " + (int)employeeNumberComboBox.getSelectedItem());
						
						while (resultSet.next()) {
							lastNameField.setText(resultSet.getString("lastName"));
							firstNameField.setText(resultSet.getString("firstName"));
							extensionField.setText(resultSet.getString("extension"));
							officeCodeComboBox.setSelectedItem(resultSet.getInt("officeCode"));
							reportsToComboBox.setSelectedItem(resultSet.getInt("reportsTo"));
							jobTitleField.setText(resultSet.getString("jobTitle"));
							emailField.setText(resultSet.getString("email"));												
						}
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            }
        });
        

        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setToolTipText("Enter the last name");
        c.gridx = 0;
        c.gridy++;
        add(lastNameLabel, c);
        lastNameField = new JTextField(20);
        c.gridx = 1;
        add(lastNameField, c);

        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setToolTipText("Enter the first name");
        c.gridx = 0;
        c.gridy++;
        add(firstNameLabel, c);
        firstNameField = new JTextField(20);
        c.gridx = 1;
        add(firstNameField, c);

        extensionLabel = new JLabel("Extension");
        extensionLabel.setToolTipText("Enter the extension");
        c.gridx = 0;
        c.gridy++;
        add(extensionLabel, c);
        extensionField = new JTextField(20);
        c.gridx = 1;
        add(extensionField, c);
        
        emailLabel = new JLabel("Email");
        emailLabel.setToolTipText("Enter the email");
        c.gridx = 0;
        c.gridy++;
        add(emailLabel, c);
        emailField = new JTextField(20);
        c.gridx = 1;
        add(emailField, c);

        officeCodeLabel = new JLabel("Office Code");
        officeCodeLabel.setToolTipText("Select the office code");
        c.gridx = 0;
        c.gridy++;
        add(officeCodeLabel, c);
        officeCodeComboBox = new JComboBox<>();
        c.gridx = 1;
        add(officeCodeComboBox, c);
        
        List<Integer> officeCodeList = getOfficeCodesFromDatabase();
        updateOfficeCodeComboBox(officeCodeList);

        reportsToLabel = new JLabel("Reports to");
        reportsToLabel.setToolTipText("test");
        c.gridx = 0;
        c.gridy ++;
        add(reportsToLabel, c);
        reportsToComboBox = new JComboBox<>();
        c.gridx = 1;
        add(reportsToComboBox, c);
        
        List<Integer> reportsToList = getEmployeeNumberFromDatabase();
        updateReportsToComboBox(reportsToList);

        jobTitleLabel = new JLabel("Job Title");
        jobTitleLabel.setToolTipText("Enter the job title");
        c.gridx = 0;
        c.gridy++;
        add(jobTitleLabel, c);
        jobTitleField = new JTextField(20);
        c.gridx = 1;
        add(jobTitleField, c);
        
        submitButton = new JButton("Add a new employee");
        submitButton.setToolTipText("Click to register this data as a new employee");
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        add(submitButton, c);
        submitButton.addActionListener(this);
        
        modifyButton = new JButton("Modify employee");
        modifyButton.setToolTipText("Click to update the employee data");
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        add(modifyButton, c);
        modifyButton.addActionListener(this);
        
        deleteButton = new JButton("Delete employee");
        deleteButton.setToolTipText("Click to delete the employee data");
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        add(deleteButton, c);
        deleteButton.addActionListener(this);


        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == modifyButton) {
            modifyToDatabase();
        }
        
        if(e.getSource() == deleteButton) {
            deleteToDatabase();
        }
        
        if(e.getSource() == submitButton) {
            submitToDatabase();
        }
    }
    
    /** Method that handles insert into the database. Gets information from all the fields in the form */
    private void submitToDatabase() {
    	DatabaseHelper db = new DatabaseHelper();
    	
    	String errorMsg = verifyInput();
    	
    	if (errorMsg.length() == 0) {
    	
	    	try {
				db.open();
				db.insertEmployee(getHighestEmployeeNumber() + 1, lastNameField.getText(), firstNameField.getText(), extensionField.getText(), emailField.getText() ,(int)officeCodeComboBox.getSelectedItem(), (int)reportsToComboBox.getSelectedItem(), jobTitleField.getText());
				db.close();
				
	            String message = "Employee registered with the employeeNumber: " + (getHighestEmployeeNumber());
	            JOptionPane.showMessageDialog(this, message);
				
				setVisible(false); 
				dispose(); 
			} 
	    	catch (SQLIntegrityConstraintViolationException e) {
	            String message = "Employee not registered, the was an SQL foriegn key constraint fault";
	            JOptionPane.showMessageDialog(this, message);
				e.printStackTrace();
			}
	    	catch (SQLException e) {
	            String message = "Employee not registered, the was an SQL exception";
	            JOptionPane.showMessageDialog(this, message);
				e.printStackTrace();
			}
	    	
	    	catch (Exception e) {
	            String message = "Employee not registered";
	            JOptionPane.showMessageDialog(this, message);
				e.printStackTrace();
			}
	    	
    	}
    	
    	else {
            JOptionPane.showMessageDialog(this, errorMsg);
    	}
    }
    
    /** Method that handles updates into the database. Gets information from all the fields in the form */
    private void modifyToDatabase() {
    	DatabaseHelper db = new DatabaseHelper();
    	String errorMsg = verifyInput();
    	
    	if (errorMsg.length() == 0) {
	    	try {
				db.open();
				db.updateEmployee((int)employeeNumberComboBox.getSelectedItem(), lastNameField.getText(), firstNameField.getText(), extensionField.getText(), emailField.getText() ,(int)officeCodeComboBox.getSelectedItem(), (int)reportsToComboBox.getSelectedItem(), jobTitleField.getText());
				db.close();
				
	            String message = "Employee is updated";
	            JOptionPane.showMessageDialog(this, message);
				
				setVisible(false); 
				dispose(); 
			} catch (SQLException e) {
	            String message = "Employee not modified";
	            JOptionPane.showMessageDialog(this, message);
				e.printStackTrace();
			}
    	}
    	else {
            JOptionPane.showMessageDialog(this, errorMsg);
    	}
    }
    
    /** Method that deletes insert into the database. Gets information from all the fields in the form */
    private void deleteToDatabase() {
    	DatabaseHelper db = new DatabaseHelper();
    	try {
    		int beforeDeletion = getNumberOfEmployees();
    		System.out.print(beforeDeletion);
			db.open();
			db.deleteEmployee((int)employeeNumberComboBox.getSelectedItem());
			db.close();
			int afterDeletion = getNumberOfEmployees();
			System.out.print(afterDeletion);
			
			if (beforeDeletion != afterDeletion) {
	            String message = "Employee is deleted";
	            JOptionPane.showMessageDialog(this, message);
				
				setVisible(false); 
				dispose(); 
			}
			else {
	            String message = "Employee is not deleted. Foreign key constraint failed";
	            JOptionPane.showMessageDialog(this, message);
				
			}
			

		} 
    	catch (SQLException e) {
            String message = "Employee not deleted";
            JOptionPane.showMessageDialog(this, message);
			e.printStackTrace();
		}
    }
    
    /** This method checks how many employees that are registered in the database. Used for verification */
    public int getNumberOfEmployees() {
    	DatabaseHelper db = new DatabaseHelper();
    	ResultSet resultSet;
    	int numberOfEmployees = 0;
    	try {
			db.open();
			resultSet = db.selectSql("SELECT COUNT(employeeNumber) as employeeNumber FROM employees;");

			
			while (resultSet.next()) {
				numberOfEmployees = resultSet.getInt("employeeNumber");
		    	return numberOfEmployees;
			}
			db.close();
		} 
    	catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return numberOfEmployees;
    }
    
    
    
    /** This method gets a list of all the office codes that are registered in the database
     * @return List<Integer> */
    private List<Integer> getOfficeCodesFromDatabase() {
        List<Integer> offices = new ArrayList<>();

        	DatabaseHelper db = new DatabaseHelper();

        	ResultSet resultSet;
			try {
	        	db.open();
				resultSet = db.selectSql("SELECT DISTINCT officeCode FROM offices");
				
	            while (resultSet.next()) {
	                int tmp = resultSet.getInt("officeCode");
	                offices.add(tmp);
	            }
	            
	            return offices;
			} catch (SQLException e) {
			
				e.printStackTrace();
			}  	
        return offices;
    }
    
    /** This method gets a list of all the employeeNumbers that are registered in the database
     * @return List<Integer> */
    private List<Integer> getEmployeeNumberFromDatabase() {
        List<Integer> employees = new ArrayList<>();

        	DatabaseHelper db = new DatabaseHelper();

        	ResultSet resultSet;
			try {
	        	db.open();
				resultSet = db.selectSql("SELECT DISTINCT employeeNumber FROM employees");
				
	            while (resultSet.next()) {
	                int tmp = resultSet.getInt("employeeNumber");
	                employees.add(tmp);
	            }
	            
	            db.close();
	            
	            return employees;
			} catch (SQLException e) {
			
				e.printStackTrace();
			}  	
        return employees;
    }
    
    /** This method returns the highest employee number registered in the database. It is used for inserting new employees
     * @return int*/
    private int getHighestEmployeeNumber() throws SQLException {
    	DatabaseHelper db = new DatabaseHelper();
    	ResultSet resultSet;
    	
		int highestNumber = 0;
    	
    	try {
    		db.open();
    		resultSet = db.selectSql("SELECT MAX(employeeNumber) as employeeNumber FROM employees;");
    		
    		while (resultSet.next()) {
    			highestNumber = resultSet.getInt("employeeNumber");
    		}
    		
    		db.close();
    		
    	} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	return highestNumber;
    }
    
    /** Fills officeCodeComboBox with office codes
     * @param List of int */
    private void updateOfficeCodeComboBox(List<Integer> items) {
        officeCodeComboBox.removeAllItems();
        for (Integer item : items) {
            officeCodeComboBox.addItem(item);
        }
    }
    
    /** Fills employeeNumberComboBox with office codes
     * @param List of int */
    private void updateEmployeeNumberComboBox(List<Integer> items) {
        employeeNumberComboBox.removeAllItems();
        for (Integer item : items) {
            employeeNumberComboBox.addItem(item);
        }
    }
    
    /** Fills updateToComboBox with office codes
     * @param List of int */
    private void updateReportsToComboBox(List<Integer> items) {
        reportsToComboBox.removeAllItems();
        for (Integer item : items) {
            reportsToComboBox.addItem(item);
        }
    }
    
    /** This method verifies all the inputs in the form. 
     * @return String */
    public String verifyInput() {
    	
    	String errorMsgOutput = "";
    	
        List<String> errorMsg = new ArrayList<String>();
    	if(lastNameField.getText().length() < 1 || lastNameField.getText().length() > 50) {
    		errorMsg.add("Last name is not formatted properly");
    	}
    	
    	if(firstNameField.getText().length() < 1 || firstNameField.getText().length() > 50) {
    		errorMsg.add("First name is not formatted properly");
    	}
    	
    	if(extensionField.getText().length() < 1 || extensionField.getText().length() > 10) {
    		errorMsg.add("Extension is not formatted properly");
    	}
    	
    	if(emailField.getText().length() < 1 || emailField.getText().length() > 100) {
    		errorMsg.add("Email is not formatted properly");
    	}
    	
    	if(jobTitleField.getText().length() < 1 || jobTitleField.getText().length() > 50) {
    		errorMsg.add("Job title is not formatted properly");
    	}
    	
    	for(String msg : errorMsg) {
    		errorMsgOutput = errorMsgOutput + msg + "\n";
    	}
    	
    	return errorMsgOutput;	
    	
    }
    

}
