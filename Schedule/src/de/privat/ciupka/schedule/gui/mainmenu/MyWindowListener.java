package de.privat.ciupka.schedule.gui.mainmenu;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import de.privat.ciupka.schedule.controller.Controller;

public class MyWindowListener implements WindowListener {

	@Override
	public void windowActivated(WindowEvent arg0) {

	}

	@Override
	public void windowClosed(WindowEvent arg0) {

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		Controller.getInstance().saveSubjects();
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
