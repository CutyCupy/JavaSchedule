package de.privat.ciupka.schedule.gui.popups;

import javax.swing.JOptionPane;

public class ErrorMessages {

	
	public static void createErrorMessage(String title, String message) {
		JOptionPane.showMessageDialog(null,
			    message,
			    title,
			    JOptionPane.ERROR_MESSAGE);

	}
	
}
