package de.privat.ciupka.schedule.gui.mainmenu;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import de.privat.ciupka.schedule.controller.Controller;
import de.privat.ciupka.schedule.controller.GUIController;

public class MyWindowListener implements WindowListener {
	
	private Controller con;
	private GUIController guiCon;
	
	public MyWindowListener() {
		con = Controller.getInstance();
		guiCon = GUIController.getInstance();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {

	}

	@Override
	public void windowClosed(WindowEvent arg0) {

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		con.saveSubjects();
		if(guiCon.getMainFrame().getContentPane().equals(guiCon.getSchedulePanel())) {
			con.askSave();
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {

	}

	@Override
	public void windowIconified(WindowEvent arg0) {

	}

	@Override
	public void windowOpened(WindowEvent arg0) {

	}

}
