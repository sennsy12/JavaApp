package com.sturla.Eksamen;

/**
 * Hello world!
 *
 */
import javax.swing.SwingUtilities;

import gui.MainWindow;


/**
 * This is the demo application for the OBJ2100 course!
 *
 */
public class App {
	
	/** main method*/
    public static void main( String[] args ){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainWindow();
            }
        });
    }
    
}