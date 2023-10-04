

package gui;


/*
 * Main application window
 * 
 * */


import java.awt.GridLayout;

import javax.swing.JFrame;


/** Main window, based on the example app*/
public class MainWindow extends JFrame {
	
	private ApplicationMenu appMenu = new ApplicationMenu();
	private MainContent content = new MainContent();

	public MainWindow() {
		
		setTitle("OBJ2000 EXAM 2023 - TEAM 2");
		setSize(1000, 500);
		setLocationRelativeTo(null);
		setLayout(new GridLayout());
		
		// add main menu
		setJMenuBar(appMenu);

		// add content
		add(content);

		setResizable(false);
		setVisible(true);
	}


}
