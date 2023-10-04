package frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DatabaseHelper;

/**
 * Represents a JFrame for executing SQL queries and displaying the result.
 * The class provides a user interface for entering SQL statements and executing them against a database.
 *
 * @author Arash
 */
public class ExecuteSql extends JFrame {

    private JTextArea sqlTextArea;
    private JButton executeButton;
    private JTextArea resultTextArea;

    /**
     * Creates a new instance of the ExecuteSql class.
     * Initializes the UI components and sets up the event listeners.
     */
    public ExecuteSql() {
        super();
        setTitle("Execute SQL");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel sqlLabel = new JLabel("Enter SQL:");
        panel.add(sqlLabel);

        sqlTextArea = new JTextArea(8, 30);
        JScrollPane sqlScrollPane = new JScrollPane(sqlTextArea);
        panel.add(sqlScrollPane);

        executeButton = new JButton("Execute");
        executeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                executeSqlQuery();
            }
        });
        panel.add(Box.createVerticalStrut(10));
        panel.add(executeButton);

        JLabel resultLabel = new JLabel("Result:");
        panel.add(Box.createVerticalStrut(10));
        panel.add(resultLabel);

        resultTextArea = new JTextArea(10, 30);
        resultTextArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultTextArea);
        panel.add(resultScrollPane);

        add(panel);
    }

    /**
     * Executes the SQL query entered by the user and displays the result.
     * Connects to the database, executes the query, and displays the result in the result text area.
     * If an error occurs during the execution, an error message dialog is shown.
     */
    private void executeSqlQuery() {
        String sql = sqlTextArea.getText();
        try {
            DatabaseHelper db = new DatabaseHelper();
            db.open();
            ResultSet resultSet = db.selectSql(sql);
            displayResultSet(resultSet);
            db.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error executing SQL query: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays the result set in the result text area.
     * Iterates through the rows of the result set and appends them to the result text area.
     *
     * @param resultSet The result set containing the query result.
     * @throws SQLException If an error occurs while accessing the result set.
     */
    private void displayResultSet(ResultSet resultSet) throws SQLException {
        StringBuilder sb = new StringBuilder();
        while (resultSet.next()) {
            String rowData = "";
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                rowData += resultSet.getString(i) + "\t";
            }
            sb.append(rowData).append("\n");
        }
        resultTextArea.setText(sb.toString());
    }
}
