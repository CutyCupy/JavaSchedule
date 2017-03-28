package de.privat.ciupka.schedule.gui.mainmenu;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import de.privat.ciupka.schedule.controller.GUIController;

/**
 * MainFrame extends JFrame.
 * This frame is, as the name already tells us, the main frame of this application.
 * This frame is everytime visible and all contentPanes will be added to this frame.
 * @author Alexander Ciupka / CutyCupy
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 5125147918665183778L;

	/**
	 * Constructor function which sets this frame visible and also adds MyWindowListener to this frame.
	 */
	public MainFrame() {
		setVisible(true);
		addWindowListener(new MyWindowListener());
		setResizable(false);
	}

	/**
	 * This function will set the given Container as new ContentPane and will also update the bounds of this frame.
	 * @param contentPane Container - new Content Pane for this frame
	 */
	public void updateContentPane(Container contentPane) {
		setContentPane(contentPane);
		setSize(contentPane.getWidth(), contentPane.getHeight());
		setLocation(WindowHandler.placeFrameToMiddle(getHeight(), getWidth()));
		repaint();
	}

	/**
	 * The given JMenuBar will be the new menubar for this frame.
	 * @param newMenu
	 */
	public void updateMenu(JMenuBar newMenu) {
		this.setJMenuBar(newMenu);
	}

	/**
	 * Main function which is called at the start of this program.
	 * @param args String[] - console parameters
	 */
	public static void main(String[] args) {
		GUIController.getInstance();
	}
}
