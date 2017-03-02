package de.privat.ciupka.schedule.gui.popups;

import javax.swing.JOptionPane;

public class Messages {
	
	public static boolean openYesNoDialog(String title, String message) {
		return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION) == 0 ? true : false;
	}

}
