

package gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/** This class defines the section where you can choose a folder for writing the customer list
 * @author Sturla */
public class FileAccessSettings extends JPanel implements ActionListener {

    private JButton openButton;
    private JFileChooser chooser;
    private JLabel chosenFolder;

    public FileAccessSettings() {
        super();
        setLayout(new GridBagLayout());

        // Initialize the file chooser
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Choose folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        openButton = new JButton("Change folder");
        openButton.setToolTipText("Click to change the folder");
        openButton.addActionListener(this); 


        chosenFolder = new JLabel("No file chosen");

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 0;
        buttonConstraints.fill = GridBagConstraints.HORIZONTAL;


        
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 1;
        labelConstraints.gridy = 0;
        labelConstraints.fill = GridBagConstraints.HORIZONTAL;
        labelConstraints.anchor = GridBagConstraints.EAST; 
        labelConstraints.insets = new Insets(0, 5, 5, 5);
        
        

        this.add(openButton, buttonConstraints);
        this.add(chosenFolder, labelConstraints);
    }

    /**this method sets the selected folder path in the listOfCustomers class by calling the static method setSelectedFolderPath
     *  @author Markus Thoresen
     *  */
    @Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == openButton) {
    	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
    	        chosenFolder.setText("Chosen folder: " + chooser.getSelectedFile().toString());
    	        listOfCustomers.setSelectedFolderPath(chooser.getSelectedFile().toString());
    	    } else {
    	        System.out.println("No Selection ");
    	    }
    	}
    }
}
